package nl.giantit.minecraft.GiantBanks.core.Updater.Database.Updates;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Database.Database;
import nl.giantit.minecraft.GiantBanks.core.Database.drivers.iDriver;

import java.util.HashMap;
import java.util.logging.Level;

public class Types {
	
	private static void update1_1() {
		iDriver db = Database.Obtain().getEngine();
		
		HashMap<String, HashMap<String, String>> fields = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("TYPE", "TEXT");
		data.put("DEFAULT", "");
		fields.put("items", data);

		db.alter("#__accounttypes").add(fields).updateQuery();
		
		data = new HashMap<String, String>();
		data.put("version", "1.1");
		
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("tableName", "accountTypes");
		
		db.update("#__versions").set(data).where(where).updateQuery();
		GiantBanks.getPlugin().getLogger().log(Level.INFO, "Updating bank account type table to version 1.1");
	}
	
	public static void run(double curV) {
		if(curV < 1.1)
			update1_1();
	}
}
