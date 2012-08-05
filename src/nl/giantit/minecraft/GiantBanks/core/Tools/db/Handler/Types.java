package nl.giantit.minecraft.GiantBanks.core.Tools.db.Handler;

import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.core.Database.Database;
import nl.giantit.minecraft.GiantBanks.core.Database.drivers.iDriver;

import java.util.ArrayList;
import java.util.HashMap;

public class Types implements IHandler {

	public Types() {}
		
	@Override
	public void init() {
		AccountType.createAccountType(0, "default", 20, 20);
		iDriver db = Database.Obtain().getEngine();
		ArrayList<HashMap<String, String>> resSet = db.select("id", "name", "maxSlots", "maxPerSlot", "items").from("#__accounttypes").execQuery();
		
		for(HashMap<String, String> res : resSet) {
			AccountType.createAccountType(Integer.parseInt(res.get("id")), res.get("name"), Integer.parseInt(res.get("maxSlots")), Integer.parseInt(res.get("maxPerSlot")), res.get("items"));
		}
	}

	@Override
	public void save() {
		iDriver db = Database.Obtain().getEngine();
		HashMap<String, AccountType> aTypes = AccountType.getAllTypes();
		
		for(AccountType aT : aTypes.values()) {
			if(!aT.isUpdated())
				continue;
			
			aT.setUpdated(false);

			int tID = aT.getTypeID();
			String name = aT.getName();
			int mS = aT.getMaxSlots();
			int mPS = aT.getMaxPerSlot();
			
			if(!aT.isNew()) {
				HashMap<String, String> fields = new HashMap<String, String>();
				fields.put("name", name);
				fields.put("maxSlots", String.valueOf(mS));
				fields.put("maxPerSlot", String.valueOf(mPS));
				fields.put("items", aT.getStorable());
				
				HashMap<String, String> where = new HashMap<String, String>();
				where.put("id", String.valueOf(tID));

				db.update("#__accounttypes").set(fields).where(where).updateQuery();
			}else{
				aT.setNew(false);
				ArrayList<String> fields = new ArrayList<String>();
				fields.add("id");
				fields.add("name");
				fields.add("maxSlots");
				fields.add("maxPerSlot");
				fields.add("items");
				
				HashMap<Integer, HashMap<String, String>> values = new HashMap<Integer, HashMap<String, String>>();
				for(int i = 0; i < fields.size(); i++) {
					HashMap<String, String> value = new HashMap<String, String>();
					String field = fields.get(i);
					if(field.equalsIgnoreCase("id")) {
						value.put("kind", "INT");
						value.put("data", String.valueOf(tID));
					}else if(field.equalsIgnoreCase("name")) {
						value.put("data", name);
					}else if(field.equalsIgnoreCase("maxSlots")) {
						value.put("data", String.valueOf(mS));
					}else if(field.equalsIgnoreCase("maxPerSlot")) {
						value.put("data", String.valueOf(mPS));
					}else if(field.equalsIgnoreCase("items")) {
						value.put("data", String.valueOf(aT.getStorable()));
					}
					
					values.put(i, value);
				}
				
				db.insert("#__accounttypes", fields, values).updateQuery();
			}
		}
	}

	@Override
	public void fullSave() {
		iDriver db = Database.Obtain().getEngine();
		HashMap<String, AccountType> aTypes = AccountType.getAllTypes();
		
		for(AccountType aT : aTypes.values()) {
			aT.setUpdated(false);

			int tID = aT.getTypeID();
			String name = aT.getName();
			int mS = aT.getMaxSlots();
			int mPS = aT.getMaxPerSlot();
			
			if(!aT.isNew()) {
				HashMap<String, String> fields = new HashMap<String, String>();
				fields.put("name", name);
				fields.put("maxSlots", String.valueOf(mS));
				fields.put("maxPerSlot", String.valueOf(mPS));
				fields.put("items", aT.getStorable());
				
				HashMap<String, String> where = new HashMap<String, String>();
				where.put("id", String.valueOf(tID));

				db.update("#__accounttypes").set(fields).where(where).updateQuery();
			}else{
				aT.setNew(false);
				ArrayList<String> fields = new ArrayList<String>();
				fields.add("id");
				fields.add("name");
				fields.add("maxSlots");
				fields.add("maxPerSlot");
				fields.add("items");
				
				HashMap<Integer, HashMap<String, String>> values = new HashMap<Integer, HashMap<String, String>>();
				for(int i = 0; i < fields.size(); i++) {
					HashMap<String, String> value = new HashMap<String, String>();
					String field = fields.get(i);
					if(field.equalsIgnoreCase("id")) {
						value.put("kind", "INT");
						value.put("data", String.valueOf(tID));
					}else if(field.equalsIgnoreCase("name")) {
						value.put("data", name);
					}else if(field.equalsIgnoreCase("maxSlots")) {
						value.put("data", String.valueOf(mS));
					}else if(field.equalsIgnoreCase("maxPerSlot")) {
						value.put("data", String.valueOf(mPS));
					}else if(field.equalsIgnoreCase("items")) {
						value.put("data", String.valueOf(aT.getStorable()));
					}
					
					values.put(i, value);
				}
				
				db.insert("#__accounttypes", fields, values).updateQuery();
			}
		}
	}
}
