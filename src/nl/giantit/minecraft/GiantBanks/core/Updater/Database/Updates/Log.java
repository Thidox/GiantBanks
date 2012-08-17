package nl.giantit.minecraft.GiantBanks.core.Updater.Database.Updates;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Database.Database;
import nl.giantit.minecraft.GiantBanks.core.Database.drivers.iDriver;

import java.util.HashMap;
import java.util.logging.Level;

public class Log {

	private static void update1_1() {
		iDriver db = Database.Obtain().getEngine();
		
		db.buildQuery("DROP TABLE #__log", false, false, false, true);
		db.updateQuery();
		GiantBanks.getPlugin().getLogger().log(Level.INFO, "Dropping old log table!");
		
		HashMap<String, HashMap<String, String>> fields = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> data = new HashMap<String, String>();
		data = new HashMap<String, String>();
		data.put("TYPE", "INT");
		data.put("LENGTH", "3");
		data.put("NULL", "false");
		data.put("A_INCR", "true");
		data.put("P_KEY", "true");
		fields.put("id", data);
		data = new HashMap<String, String>();
		data.put("TYPE", "INT");
		data.put("LENGTH", "3");
		data.put("NULL", "true");
		fields.put("type", data);
		data = new HashMap<String, String>();
		data.put("TYPE", "VARCHAR");
		data.put("LENGTH", "100");
		data.put("NULL", "true");
		fields.put("typeName", data);
		data = new HashMap<String, String>();
		data.put("TYPE", "VARCHAR");
		data.put("LENGTH", "100");
		data.put("NULL", "true");
		fields.put("user", data);
		data = new HashMap<String, String>();
		data.put("TYPE", "VARCHAR");
		data.put("LENGTH", "100");
		data.put("NULL", "true");
		fields.put("data", data);
		data = new HashMap<String, String>();
		data.put("TYPE", "BIGINT");
		data.put("LENGTH", "50");
		data.put("NULL", "false");
		data.put("DEFAULT", "0");
		fields.put("date", data);
		
		db.create("#__log").fields(fields).Finalize().updateQuery();
		
		data = new HashMap<String, String>();
		data.put("version", "1.1");
		
		HashMap<String, String> where = new HashMap<String, String>();
		where.put("tableName", "log");
		
		db.update("#__versions").set(data).where(where).updateQuery();
		GiantBanks.getPlugin().getLogger().log(Level.INFO, "Updating log table to version 1.1");
	}
	
	public static void run(double curV) {
		if(curV < 1.1)
			update1_1();
	}
}
