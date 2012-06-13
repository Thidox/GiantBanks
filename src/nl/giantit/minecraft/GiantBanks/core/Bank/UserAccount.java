package nl.giantit.minecraft.GiantBanks.core.Bank;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAccount {

	private static HashMap<String, UserAccount> accounts = new HashMap<String, UserAccount>();
	
	private HashMap<String, Integer> items = new HashMap<String, Integer>();
	private HashMap<String, ArrayList<Integer>> cache = new HashMap<String, ArrayList<Integer>>();
	private HashMap<Integer, BankSlot> slots = new HashMap<Integer, BankSlot>();
	
	private Integer aID;
	private String owner;
	private String rawData;
	private Boolean isUpdated = false;
	private AccountType type;
		
	private UserAccount(String p, String data) {
		this(null, p, data);
		this.isUpdated = true;
	}
	
	private UserAccount(Integer id, String p, String data) {
		this(id, p, data, null);
	}
	
	private UserAccount(Integer id, String p, String data, AccountType type) {
		this.aID = id;
		this.owner = p;
		this.rawData = data;
		this.type = type;
		
		//this.parseData();
	}
	
	public Integer getAccountID() {
		return this.aID;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public String getRawData() {
		//return this.getRaw();
		return null;
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
	
	public String add(String item, int amount) {
		if(this.items.containsKey(item)) {
			int a = this.items.remove(item);
			if(this.type.getMaxPerSlot() > a) {
				this.items.put(item, (amount + a));
				return null;
			}else{
				return "";
			}
		}else{
			int mS = this.type.getMaxSlots();
			if(mS <= 0 || mS > this.items.size()) {
				return null;
			}else{
				return "";
			}
		}
	}
	
	public void get(String item, int amount) {
		
	}
	
	public void getAll(String item) {
		
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
	
	public static HashMap<String, UserAccount> getAllAccounts() {
		return accounts;
	}
	
}
