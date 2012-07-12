package nl.giantit.minecraft.GiantBanks.Bank;

import java.util.HashMap;

public class AccountType {

	private static HashMap<Integer, String> typeByID = new HashMap<Integer, String>();
	private static HashMap<String, AccountType> types = new HashMap<String, AccountType>();
	
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	
	private Integer typeID;
	private String name;
	private String rawData;
	private Boolean isUpdated = false;
	
	private AccountType(Integer id, String name, String data) {
		this.typeID = id;
		this.name = name;
		this.rawData = data;
	}
	
	public Integer getTypeID() {
		return this.typeID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getRawData() {
		//return this.getRaw();
		return null;
	}
	
	public int getMaxSlots() {
		
		return 20;
	}
	
	public int getMaxPerSlot() {
		
		return 20;
	}
	
	public static AccountType createAccountType(int id, String p, String data) {
		if(!types.containsKey(p)) {
			AccountType AT = new AccountType(id, p, data);
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
