package nl.giantit.minecraft.GiantBanks.core.Tools.db;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Database.drivers.iDriver;
import nl.giantit.minecraft.GiantBanks.core.Updater.UpdateType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class dbInit {
	
	private iDriver dbDriver;

	private void init() {
		if(!this.dbDriver.tableExists("#__versions")) {
			HashMap<String, HashMap<String, String>> fields = new HashMap<String, HashMap<String, String>>();
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("TYPE", "VARCHAR");
			data.put("LENGTH", "100");
			data.put("NULL", "false");
			fields.put("tableName", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "DOUBLE");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			data.put("DEFAULT", "1.0");
			fields.put("version", data);
			
			this.dbDriver.create("#__versions").fields(fields).Finalize();
			this.dbDriver.updateQuery();
			
			GiantBanks.log.log(Level.INFO, "Revisions table successfully created!");
		}
		
		if(!this.dbDriver.tableExists("#__log")){
			ArrayList<String> field = new ArrayList<String>();
			field.add("tablename");
			field.add("version");
			
			HashMap<Integer, HashMap<String, String>> d = new HashMap<Integer, HashMap<String, String>>();
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("data", "log");
			d.put(0, data);
			
			data = new HashMap<String, String>();
			data.put("data", "1.0");
			d.put(1, data);
			
			this.dbDriver.insert("#__versions", field, d).Finalize();
			this.dbDriver.updateQuery();
			
			HashMap<String, HashMap<String, String>> fields = new HashMap<String, HashMap<String, String>>();
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
			fields.put("user", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "VARCHAR");
			data.put("LENGTH", "100");
			data.put("NULL", "true");
			fields.put("data", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "INT");
			data.put("LENGTH", "50");
			data.put("NULL", "false");
			data.put("DEFAULT", "0");
			fields.put("date", data);
			
			this.dbDriver.create("#__log").fields(fields).Finalize();
			this.dbDriver.updateQuery();
			
			GiantBanks.log.log(Level.INFO, "Logging table successfully created!");
		}
		
		if(!this.dbDriver.tableExists("#__banks")) {
			ArrayList<String> field = new ArrayList<String>();
			field.add("tablename");
			field.add("version");
			
			HashMap<Integer, HashMap<String, String>> d = new HashMap<Integer, HashMap<String, String>>();
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("data", "banks");
			d.put(0, data);
			
			data = new HashMap<String, String>();
			data.put("data", "1.0");
			d.put(1, data);
			
			this.dbDriver.insert("#__versions", field, d).Finalize();
			this.dbDriver.updateQuery();
			
			HashMap<String, HashMap<String, String>> fields = new HashMap<String, HashMap<String, String>>();
			data = new HashMap<String, String>();
			data.put("TYPE", "INT");
			data.put("LENGTH", "3");
			data.put("NULL", "false");
			data.put("A_INCR", "true");
			data.put("P_KEY", "true");
			fields.put("id", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "VARCHAR");
			data.put("LENGTH", "100");
			data.put("NULL", "false");
			fields.put("name", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "VARCHAR");
			data.put("LENGTH", "100");
			data.put("NULL", "true");
			fields.put("world", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "DOUBLE");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("locMinX", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "DOUBLE");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("locMinY", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "DOUBLE");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("locMinZ", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "DOUBLE");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("locMaxX", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "DOUBLE");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("locMaxY", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "DOUBLE");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("locMaxZ", data);
			
			this.dbDriver.create("#__banks").fields(fields).Finalize();
			this.dbDriver.updateQuery();
			
			GiantBanks.log.log(Level.INFO, "Banks table successfully created!");
		}
		
		if(!this.dbDriver.tableExists("#__accounts")) {
			ArrayList<String> field = new ArrayList<String>();
			field.add("tablename");
			field.add("version");
			
			HashMap<Integer, HashMap<String, String>> d = new HashMap<Integer, HashMap<String, String>>();
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("data", "accounts");
			d.put(0, data);
			
			data = new HashMap<String, String>();
			data.put("data", "1.0");
			d.put(1, data);
			
			this.dbDriver.insert("#__versions", field, d).Finalize();
			this.dbDriver.updateQuery();
			
			HashMap<String, HashMap<String, String>> fields = new HashMap<String, HashMap<String, String>>();
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
			fields.put("accType", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "VARCHAR");
			data.put("LENGTH", "100");
			data.put("NULL", "false");
			fields.put("player", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "TEXT");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("data", data);
			
			this.dbDriver.create("#__accounts").fields(fields).Finalize();
			this.dbDriver.updateQuery();
			
			GiantBanks.log.log(Level.INFO, "Bank accounts table successfully created!");
		}
		
		if(!this.dbDriver.tableExists("#__accountTypes")) {
			ArrayList<String> field = new ArrayList<String>();
			field.add("tablename");
			field.add("version");
			
			HashMap<Integer, HashMap<String, String>> d = new HashMap<Integer, HashMap<String, String>>();
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("data", "accountTypes");
			d.put(0, data);
			
			data = new HashMap<String, String>();
			data.put("data", "1.0");
			d.put(1, data);
			
			this.dbDriver.insert("#__versions", field, d).Finalize();
			this.dbDriver.updateQuery();
			
			HashMap<String, HashMap<String, String>> fields = new HashMap<String, HashMap<String, String>>();
			data = new HashMap<String, String>();
			data.put("TYPE", "INT");
			data.put("LENGTH", "3");
			data.put("NULL", "false");
			data.put("A_INCR", "true");
			data.put("P_KEY", "true");
			fields.put("id", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "VARCHAR");
			data.put("LENGTH", "100");
			data.put("NULL", "false");
			fields.put("player", data);
			data = new HashMap<String, String>();
			data.put("TYPE", "TEXT");
			data.put("LENGTH", null);
			data.put("NULL", "false");
			fields.put("data", data);
			
			this.dbDriver.create("#__accountTypes").fields(fields).Finalize();
			this.dbDriver.updateQuery();
			
			GiantBanks.log.log(Level.INFO, "Bank account type table successfully created!");
		}
	}
	
	public dbInit(GiantBanks plugin) {
		this.dbDriver = plugin.getDB().getEngine();

		this.init();
		plugin.getUpdater().Update(UpdateType.DATABASE);
	}
	
}
