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
		
	}

	@Override
	public void fullSave() {
		
	}
}
