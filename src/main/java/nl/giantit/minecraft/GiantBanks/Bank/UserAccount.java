package nl.giantit.minecraft.GiantBanks.Bank;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Items.ItemID;
import nl.giantit.minecraft.GiantBanks.core.Logger.Logger;
import nl.giantit.minecraft.GiantBanks.core.Logger.LoggerType;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.dbType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserAccount {

	private static HashMap<String, UserAccount> accounts = new HashMap<String, UserAccount>();
	
	private Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private Integer aID;
	private String owner;
	private String rawData;
	private Boolean isUpdated = false;
	private AccountType type;
	
	private ConcurrentHashMap<String, Collection<BankSlot>> slots = new ConcurrentHashMap<String, Collection<BankSlot>>();
	private int usedSlots = 0;
	
	private BankSlot fillNewSlot(int usedSlots, String item, int amount, ItemID iID) {
		BankSlot bS = new BankSlot(usedSlots, item, amount, iID);
		this.isUpdated = true;
		
		return bS;
	}
	
	private void parseData() {
		String[] d = this.rawData.split("-;-");
		for(String bS : d) {
			if(bS.length() <= 0)
				continue;
			
			BankSlot b = new BankSlot(bS);
			if(!this.slots.containsKey(b.getItem())) {
				this.usedSlots++;
				Collection<BankSlot> slots = Collections.synchronizedCollection(new ArrayList<BankSlot>());
				slots.add(b);
				
				this.slots.put(b.getItem(), slots);
			}else{
				this.usedSlots++;
				Collection<BankSlot> slots = this.slots.remove(b.getItem());;
				slots.add(b);
				
				this.slots.put(b.getItem(), slots);
			}
		}
		
	}
	
	private String rawData() {
		String d = "";
		
		for(Collection<BankSlot> s : this.slots.values()) {
			for(BankSlot slot : s) {
				d += slot + "-;-";
			}
		}
		
		return d;
	}
	
	private UserAccount(String p, String data) {
		this(null, p, data);
		this.isUpdated = true;
	}
	
	private UserAccount(Integer id, String p, String data) {
		this(id, p, data, null);
	}
	
	private UserAccount(Integer id, String p, String data, AccountType type) {
		if(null == type)
			type = AccountType.getType(0);
			
		this.aID = id;
		this.owner = p;
		this.rawData = data;
		this.type = type;
		
		if(null != this.rawData)
			this.parseData();
	}
	
	public void reset(String data) {
		this.slots = new ConcurrentHashMap<String, Collection<BankSlot>>();
		this.usedSlots = 0;
		
		this.rawData = data;
		
		if(null != this.rawData)
			this.parseData();
	}
	
	public void setAccountID(int id) {
		this.aID = id;
	}
	
	public Integer getAccountID() {
		return this.aID;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public String getRawData() {
		return this.rawData();
	}
	
	public AccountType setType(AccountType aT) {
		this.type = aT;
		
		return this.type;
	}
	
	public AccountType getType() {
		return this.type;
	}
	
	public Boolean isUpdated() {
		return this.isUpdated;
	}
	
	public Boolean setUpdated(Boolean updated) {
		this.isUpdated = updated;
		return this.isUpdated;
	}
	
	public int add(ItemID iID, int amount) {
		if(iID == null)
			return -2;
		
		if(this.type.isStorable(iID)) {
			String item = GiantBanks.getPlugin().getItemHandler().getItemNameByID(iID.getId(), iID.getType());
			int mS = this.type.getMaxSlots();
			int amt = amount;
			
			if(this.slots.containsKey(item)) {
				Collection<BankSlot> bankslots = this.slots.get(item);
				for(BankSlot bS : bankslots) {
					if(!bS.contains(iID)) {
						//Durability missmatch? Or perhaps a misscache?
						//What ever the case, we need to remove that entry from the cache!
						bankslots.remove(bS);
						continue;
					}
					
					int a = bS.getAmount() + amt;
					int d = this.type.getMaxPerSlot() - bS.getAmount();
					
					if(this.type.getMaxPerSlot() <= 0 || a <= this.type.getMaxPerSlot()) {
						amt = 0;
						bS.setAmount(a);
						break;
					}else if(d > 0) {
						amt -= d;
						bS.setAmount(d, true);
						if(amt <= 0)
							break;
					}
				}
				
				if(amt > 0) {
					while(amt > 0) {
						if(mS <= 0 || mS > this.usedSlots) {
							int usedSlots = bankslots.size();
							if(amt - this.type.getMaxPerSlot() > 0) {
								amt -= this.type.getMaxPerSlot();
								bankslots.add(this.fillNewSlot(usedSlots, item, this.type.getMaxPerSlot(), iID));
								usedSlots++;
								this.usedSlots++;
							}else{
								bankslots.add(this.fillNewSlot(usedSlots, item, amt, iID));
								usedSlots++;
								this.usedSlots++;
								amt = 0;
								break;
							}
						}else{
							break;
						}
					}
					
					this.slots.put(item, bankslots);
				}
				
				if(amt == 0) {
					//Call to Sync in case caching is off for bank accounts
					this.isUpdated = true;
					GiantBanks.getPlugin().getSync().callUpdate(dbType.ACCOUNTS);
					return 0;
				}else{
					if(amt < amount) {
						//message saying x items have not been added
						this.isUpdated = true;
						GiantBanks.getPlugin().getSync().callUpdate(dbType.ACCOUNTS);
						return amt;
					}
				}
				
				return -1; //Return message saying no available slots.
			}else{
				Collection<BankSlot> bankslots = Collections.synchronizedCollection(new ArrayList<BankSlot>());
				while(amt > 0) {
					if(mS <= 0 || mS > this.usedSlots) {
						int usedSlots = bankslots.size();
						if(amt - this.type.getMaxPerSlot() > 0) {
							amt -= this.type.getMaxPerSlot();
							bankslots.add(this.fillNewSlot(usedSlots, item, this.type.getMaxPerSlot(), iID));
							usedSlots++;
							this.usedSlots++;
						}else{
							bankslots.add(this.fillNewSlot(usedSlots, item, amt, iID));
							usedSlots++;
							this.usedSlots++;
							amt = 0;
							break;
						}
					}else{
						break;
					}
				}
				
				this.slots.put(item, bankslots);
				
				if(amt == 0) {
					this.isUpdated = true;
					//Call to Sync in case caching is off for bank accounts
					GiantBanks.getPlugin().getSync().callUpdate(dbType.ACCOUNTS);
					return 0;
				}else{
					//message saying x items have not been added
					return amt;
				}
			}
		}else{
			return -3;
		}
	}
	
	public int has(ItemID iID) {
		if(iID == null)
			return 0;
		
		String item = GiantBanks.getPlugin().getItemHandler().getItemNameByID(iID.getId(), iID.getType());
		
		if(!this.slots.containsKey(item)) {
			return 0;
		}

		int amt = 0;
		Collection<BankSlot> bankslots = this.slots.get(item);
		for(BankSlot bS : bankslots) {
			if(!bS.contains(iID)) {
				//Durability missmatch? Or perhaps a misscache?
				//What ever the case, we need to remove that entry from the cache!
				bankslots.remove(bS);
				continue;
			}
			
			amt += bS.getAmount();
		}
		
		
		return amt;
	}
	
	public int get(ItemID iID, int amount) {
		if(iID == null)
			return -2;
		
		String item = GiantBanks.getPlugin().getItemHandler().getItemNameByID(iID.getId(), iID.getType());
		
		if(!this.slots.containsKey(item)) {
			return -1;
		}

		int amt = amount;
		Collection<BankSlot> bankslots = this.slots.get(item);
		Collection<BankSlot> remove = Collections.synchronizedCollection(new ArrayList<BankSlot>());
		
		for(BankSlot bS : bankslots) {
			if(!bS.contains(iID)) {
				//Durability missmatch? Or perhaps a misscache?
				//What ever the case, we need to remove that entry from the cache!
				bankslots.remove(bS);
				continue;
			}
			
			int a = bS.getAmount() - amt;
			
			if(a <= 0) {
				amt -= bS.getAmount();
				bS.setAmount(0);
				
				//Slot no longer holds any data, keeping it makes no sense.
				remove.add(bS);
				this.usedSlots--;
			}else{
				bS.setAmount(amt, false);
				amt = 0;
				
				if(bS.getAmount() <= 0) {
					//Slot no longer holds any data, keeping it makes no sense.
					remove.add(bS);
					this.usedSlots--;
				}
				
				break;
			}
			
			if(amt == 0)
				break;
		}
		
		bankslots.removeAll(remove);
		
		this.slots.put(item, bankslots);
		
		if(amt == 0) {
			//Call to Sync in case caching is off for bank accounts
			this.isUpdated = true;
			GiantBanks.getPlugin().getSync().callUpdate(dbType.ACCOUNTS);
			return 0;
		}else{
			return amt;
		}
	}
	
	public int getAll(ItemID iID) {
		if(iID == null)
			return -2;
		
		String item = GiantBanks.getPlugin().getItemHandler().getItemNameByID(iID.getId(), iID.getType());
		
		if(!this.slots.containsKey(item)) {
			return -1;
		}
		
		int total = 0;
		Collection<BankSlot> bankslots = this.slots.remove(item);
		
		for(BankSlot bS : bankslots) {
			if(!bS.contains(iID)) {
				//Durability missmatch? Or perhaps a misscache?
				//What ever the case, we need to remove that entry from the cache!
				bankslots.remove(bS);
				continue;
			}
			
			total += bS.getAmount();
			
			//Slot no longer holds any data, keeping it makes no sense.
			this.usedSlots--;
		}
		
		//Call to Sync in case caching is off for bank accounts
		this.isUpdated = true;
		GiantBanks.getPlugin().getSync().callUpdate(dbType.ACCOUNTS);
		return total;
	}
	
	public HashMap<String, Integer> getItemList() {
		HashMap<String, Integer> items = new HashMap<String, Integer>();
		
		for(Map.Entry<String, Collection<BankSlot>> slot : this.slots.entrySet()) {
			int amount = 0;
			for(BankSlot s : slot.getValue()) {
				amount += s.getAmount();
			}
			
			items.put(slot.getKey(), amount);
		}
		
		return items;
	}
	
	public void eraseAll() {
		//Very dangerous removes ALL items stored in this account!
		this.slots = new ConcurrentHashMap<String, Collection<BankSlot>>();
		
		this.isUpdated = true;
		GiantBanks.getPlugin().getSync().callUpdate(dbType.ACCOUNTS);
	}
	
	public static UserAccount getUserAccount(String p) {
		if(accounts.containsKey(p))
			return accounts.get(p);
		
		return null;
	}
	
	public static UserAccount createUserAccount(String p) {
		return createUserAccount(p, null);
	}
	
	public static UserAccount createUserAccount(String p, String data) {
		if(!accounts.containsKey(p)) {
			UserAccount UA = new UserAccount(p, data);				
			accounts.put(p, UA);
			
			Logger.Log(LoggerType.UAC, p,
						"{id=" + String.valueOf(UA.getAccountID()) + ";" +
						"owner=" + UA.getOwner() + ";" +
						"aT=" + String.valueOf(UA.getType().getTypeID()) + ";" +
						"aTn=" + UA.getType().getName() + ";" +
						"data=" + UA.getRawData() + ";}");
			return UA;
		}
		
		return accounts.get(p);
	}
	
	public static UserAccount createUserAccount(int id, String p, String data) {
		if(!accounts.containsKey(p)) {
			UserAccount UA = new UserAccount(id, p, data);				
			accounts.put(p, UA);
			return UA;
		}
		
		return accounts.get(p);
	}
	
	public static UserAccount createUserAccount(int id, String p, String data, AccountType type) {
		if(!accounts.containsKey(p)) {
			UserAccount UA = new UserAccount(id, p, data, type);
			accounts.put(p, UA);
			return UA;
		}
		
		return accounts.get(p);
	}
	
	public static UserAccount createUserAccount(int id, String p, String data, AccountType type, Boolean overwrite) {
		if(!accounts.containsKey(p) || overwrite) {
			UserAccount UA = new UserAccount(id, p, data, type);
			accounts.put(p, UA);
			return UA;
		}
		
		return accounts.get(p);
	}
	
	public static HashMap<String, UserAccount> getAllAccounts() {
		return accounts;
	}
	
}
///b s -i:cookie -a 5
