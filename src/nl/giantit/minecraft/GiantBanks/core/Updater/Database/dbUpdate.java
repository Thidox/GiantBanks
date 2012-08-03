package nl.giantit.minecraft.GiantBanks.core.Updater.Database;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Updater.Database.Updates.Accounts;
import nl.giantit.minecraft.GiantBanks.core.Updater.Database.Updates.Types;

import java.util.ArrayList;
import java.util.HashMap;

public class dbUpdate {

	private static double curA = 1.0, curAT = 1.1;
	
	public static void Update() {
		ArrayList<HashMap<String, String>> resSet = GiantBanks.getPlugin().getDB().getEngine().select("tablename", "version").from("#__versions").execQuery();

		for(int i = 0; i < resSet.size(); i++) {
			HashMap<String, String> res = resSet.get(i);
			String table = res.get("tableName");
			Double version = Double.parseDouble(res.get("version"));
			
			if(table.equalsIgnoreCase("accounts") && version < curA) {
				Accounts.run(version);
			}else if(table.equalsIgnoreCase("accountTypes") && version < curAT) {
				Types.run(version);
			}
		}
	}
	
}
