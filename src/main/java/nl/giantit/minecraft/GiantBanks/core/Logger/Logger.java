package nl.giantit.minecraft.GiantBanks.core.Logger;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Database.Database;
import nl.giantit.minecraft.GiantBanks.core.Database.drivers.iDriver;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

public class Logger {

	public static void Log(String data) {
		Log("Unknown", data);
	}
	
	public static void Log(String n, String data) {
		Log(LoggerType.UNKNOWN, n, data);
	}
	
	public static void Log(Player p, String data) {
		Log(LoggerType.UNKNOWN, p, data);
	}
	
	public static void Log(CommandSender s, String data) {
		Log(LoggerType.UNKNOWN, s, data);
	}
	
	public static void Log(LoggerType t, String data) {
		Log(t, "Unknown", data);
	}
	
	public static void Log(LoggerType t, CommandSender s, String data) {
		if(s instanceof Player) {
			Log(t, (Player) s, data);
		}else{
			Log(t, "Console", data);
		}
	}
	
	public static void Log(LoggerType t, Player p, String data) {
		Log(t, p.getName(), data);
	}
	
	public static void Log(LoggerType t, String n, String data) {
		config conf = config.Obtain();
		if(conf.getBoolean("GiantBanks.log.useLogging")) {
			if(conf.getBoolean("GiantBanks.log.log." + t.getName().toLowerCase().replace(" ", "."))) {
				iDriver DB = Database.Obtain().getEngine();
				int type = t.getID();
				
				ArrayList<String> fields = new ArrayList<String>();
				ArrayList<HashMap<Integer, HashMap<String, String>>> values = new ArrayList<HashMap<Integer, HashMap<String, String>>>();
		
				fields.add("type");
				fields.add("typeName");
				fields.add("user");
				fields.add("data");
				fields.add("date");
				
				HashMap<Integer, HashMap<String, String>> tmp = new HashMap<Integer, HashMap<String, String>>();
				int i = 0;
				for(String field : fields) {
					HashMap<String, String> temp = new HashMap<String, String>();
					if(field.equalsIgnoreCase("type")) {
						temp.put("kind", "INT");
						temp.put("data", String.valueOf(type));
						tmp.put(i, temp);
					}else if(field.equalsIgnoreCase("typeName")) {
						temp.put("data", t.getName());
						tmp.put(i, temp);
					}else if(field.equalsIgnoreCase("user")) {
						temp.put("data", n);
						tmp.put(i, temp);
					}else if(field.equalsIgnoreCase("data")) {
						temp.put("data", data);
						tmp.put(i, temp);
					}else if(field.equalsIgnoreCase("date")) {
						temp.put("data", String.valueOf(Logger.getTimestamp()));
						tmp.put(i, temp);
					}
					i++;
				}
				
				values.add(tmp);
				GiantBanks.getPlugin().getLogger().log(Level.INFO, String.valueOf(Logger.getTimestamp()));
				DB.insert("#__log", fields, values).updateQuery();
			}
		}
	}
	
	public static long getTimestamp() {
		
		Date d = new Date();
		return d.getTime();
	}
}
