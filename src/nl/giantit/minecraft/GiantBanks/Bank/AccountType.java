package nl.giantit.minecraft.GiantBanks.Bank;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Items.ItemID;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountType {

	private static HashMap<Integer, String> typeByID = new HashMap<Integer, String>();
	private static HashMap<String, AccountType> types = new HashMap<String, AccountType>();
	
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	private Items iH = GiantBanks.getPlugin().getItemHandler();
	
	private Integer typeID;
	private String name;
	private int maxSlots = 20;
	private int maxPerSlot = 20;
	private Boolean isUpdated = false;

	private ArrayList<String> allowed = new ArrayList<String>();
	private ArrayList<String> disallowed = new ArrayList<String>(); 

	private AccountType(Integer id, String name) {
		this(id, name, 20, 20);
	}
	
	private AccountType(Integer id, String name, int maxSlot) {
		this(id, name, maxSlot, 20);
	}
	
	private AccountType(Integer id, String name, int maxSlot, int maxPerSlot) {
		this(id, name, maxSlot, maxPerSlot, null);
	}
	
	private AccountType(Integer id, String name, int maxSlot, int maxPerSlot, String item) {
		this.typeID = id;
		this.name = name;
		this.maxSlots = maxSlot;
		this.maxPerSlot = maxPerSlot;
		
		if(null != item) {
			String[] items = item.split(";");
			for(String i : items) {
				if(i.startsWith("+")) {
					i = i.replaceFirst("\\+", "");
					allowed.add(i.toLowerCase());
				}else{
					i = i.replace("^-", "");
					disallowed.add(i.toLowerCase());
				}
			}
		}
	}
	
	public Integer getTypeID() {
		return this.typeID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Boolean isStorable(ItemID item) {
		if(item == null)
			return false;
		
		return this.isStorable(iH.getItemNameByID(item.getId(), (null == item.getType() ? null : item.getType())));
	}
	
	public Boolean isStorable(String item) {
		if(item == null)
			return false;
		
		if(allowed.contains(item.toLowerCase())) {
			return true;
		}
		
		if(allowed.size() > 0)
			return false;
		
		return !disallowed.contains(item.toLowerCase());
	}
	
	public int getMaxSlots() {
		return this.maxSlots;
	}
	
	public int getMaxPerSlot() {
		return this.maxPerSlot;
	}
	
	public static AccountType createAccountType(int id, String p) {
		return createAccountType(id, p, 20, 20);
	}
	
	public static AccountType createAccountType(int id, String p, int maxSlot) {
		return createAccountType(id, p, maxSlot, 20);
	}
	
	public static AccountType createAccountType(int id, String p, int maxSlot, int maxPerSlot) {
		return createAccountType(id, p, maxSlot, maxPerSlot, null);
	}
	
	public static AccountType createAccountType(int id, String p, int maxSlot, int maxPerSlot, String item) {
		if(!types.containsKey(p)) {
			AccountType AT = new AccountType(id, p, maxSlot, maxPerSlot, item);
			types.put(p, AT);
			typeByID.put(id, p);
			return AT;
		}
		
		return types.get(p);
	}
	
	public static AccountType getType(int id) {
		if(typeByID.containsKey(id)) {
			String n = typeByID.get(id);
			if(types.containsKey(n)) {
				return types.get(n);
			}else
				typeByID.remove(id);
		}
		
		return null;
	}
	
	public static AccountType getType(String name) {
		if(types.containsKey(name)) {
			return types.get(name);
		}
		
		return null;
	}
}
