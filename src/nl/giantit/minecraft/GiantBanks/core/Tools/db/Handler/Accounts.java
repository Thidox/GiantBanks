package nl.giantit.minecraft.GiantBanks.core.Tools.db.Handler;

import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.Bank.UserAccount;
import nl.giantit.minecraft.GiantBanks.core.Database.Database;
import nl.giantit.minecraft.GiantBanks.core.Database.drivers.iDriver;

import java.util.ArrayList;
import java.util.HashMap;

public class Accounts implements IHandler {

	public Accounts() {}
	
	@Override
	public void init() {
		iDriver db = Database.Obtain().getEngine();
		ArrayList<HashMap<String, String>> resSet = db.select("id", "accType", "player", "data").from("#__accounts").execQuery();
		
		for(HashMap<String, String> res : resSet) {
			int id = Integer.parseInt(res.get("id"));
			
			AccountType type = null;
			if(res.get("accType") != null && !res.get("accType").equals("")) {
				int typeID = Integer.parseInt(res.get("accType"));
				type = AccountType.getType(typeID);
			}else{
				type = AccountType.getType("default");
			}
			
			UserAccount.createUserAccount(id, res.get("player"), res.get("data"), type);
		}
	}

	@Override
	public void save() {
		iDriver db = Database.Obtain().getEngine();
		HashMap<String, UserAccount> uAccounts = UserAccount.getAllAccounts();
		for(UserAccount uA : uAccounts.values()) {
			if(!uA.isUpdated())
				continue;
			
			uA.setUpdated(false);
			
			Integer aID = uA.getAccountID();
			AccountType type = uA.getType();
			String tID = (type.getTypeID() != null) ? type.getTypeID().toString() : "";
			String owner = uA.getOwner();
			String data = uA.getRawData();
			
			if(aID != null) {
				HashMap<String, String> fields = new HashMap<String, String>();
				fields.put("data", data);
				fields.put("accType", tID);
				
				HashMap<String, String> where = new HashMap<String, String>();
				where.put("id", aID.toString());
				where.put("player", owner);
				
				db.update("#__accounts").set(fields).where(where).updateQuery();
			}else{
				ArrayList<String> fields = new ArrayList<String>();
				fields.add("accType");
				fields.add("player");
				fields.add("data");
				
				HashMap<Integer, HashMap<String, String>> values = new HashMap<Integer, HashMap<String, String>>();
				
				for(int i = 0; i < fields.size(); i++) {
					HashMap<String, String> value = new HashMap<String, String>();
					String field = fields.get(i);
					if(field.equalsIgnoreCase("accType")) {
						value.put("kind", "INT");
						value.put("data", tID.toString());
					}else if(field.equalsIgnoreCase("player")) {
						value.put("data", owner);
					}else if(field.equalsIgnoreCase("data")) {
						value.put("data", data);
					}
					
					values.put(i, value);
				}
				
				HashMap<String, String> where = new HashMap<String, String>();
				where.put("player", owner);
				
				db.insert("#__accounts", fields, values).updateQuery();
				ArrayList<HashMap<String, String>> resSet = db.select("id").from("#__accounts").where(where).execQuery();
				uA.setAccountID(Integer.parseInt(resSet.get(0).get("id")));
			}
		}
	}

	@Override
	public void fullSave() {
		iDriver db = Database.Obtain().getEngine();
		HashMap<String, UserAccount> uAccounts = UserAccount.getAllAccounts();
		
		for(UserAccount uA : uAccounts.values()) {
			uA.setUpdated(false);
			
			Integer aID = uA.getAccountID();
			AccountType type = uA.getType();
			String tID = (type.getTypeID() != null) ? type.getTypeID().toString() : "";
			String owner = uA.getOwner();
			String data = uA.getRawData();
			
			if(aID != null) {
				HashMap<String, String> fields = new HashMap<String, String>();
				fields.put("data", data);
				fields.put("accType", tID);
				
				HashMap<String, String> where = new HashMap<String, String>();
				where.put("id", aID.toString());
				where.put("player", owner);
				
				db.update("#__accounts").set(fields).where(where).updateQuery();
			}else{
				ArrayList<String> fields = new ArrayList<String>();
				fields.add("accType");
				fields.add("player");
				fields.add("data");
				
				HashMap<Integer, HashMap<String, String>> values = new HashMap<Integer, HashMap<String, String>>();
				
				for(int i = 0; i < fields.size(); i++) {
					HashMap<String, String> value = new HashMap<String, String>();
					String field = fields.get(i);
					if(field.equalsIgnoreCase("accType")) {
						value.put("kind", "INT");
						value.put("data", tID.toString());
					}else if(field.equalsIgnoreCase("player")) {
						value.put("data", owner);
					}else if(field.equalsIgnoreCase("data")) {
						value.put("data", data);
					}
					
					values.put(i, value);
				}
				
				db.insert("#__accounts", fields, values).updateQuery();
			}
		}
	}
}
