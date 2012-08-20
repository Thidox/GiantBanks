package nl.giantit.minecraft.GiantBanks.core;

import nl.giantit.minecraft.GiantBanks.GiantBanks;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 *
 * @author Giant
 */
public class config {
	
	private static config instance = null;
	private HashMap<String, Object> conf = new HashMap<String, Object>();
	
	private YamlConfiguration c;
	private File file;
	private double yamlVersion = 0.1;
	
	private config() {
	}
	
	public void loadConfig(File file) {
		this.file = file;
		this.c = YamlConfiguration.loadConfiguration(this.file);
		
		double v = this.getDouble(GiantBanks.getPlugin().getName() + ".global.version");
		if(v < this.yamlVersion) {
			GiantBanks.getPlugin().getLogger().log(Level.INFO, "Your conf.yml has ran out of date. Updating now!");
			File oconfigFile = new File(GiantBanks.getPlugin().getDir(), "conf.yml." + v + ".bak");
			this.file.renameTo(oconfigFile);
			GiantBanks.getPlugin().extract("conf.yml");
			this.c = YamlConfiguration.loadConfiguration(this.file);
		}
	}
	
	public String getString(String setting) {
		return this.c.getString(setting, "");
	}
	
	public List<String> getStringList(String setting) {
		return this.c.getStringList(setting);
	}
	
	public Boolean getBoolean(String setting) {
		return this.c.getBoolean(setting, false);
	}
	
	public Integer getInt(String setting) {
		return this.c.getInt(setting, 0);
	}
	
	public Double getDouble(String setting) {
		return this.c.getDouble(setting, 0);
	}

	public HashMap<String, ?> getMap(String setting) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		Set<String> t = c.getConfigurationSection(setting).getKeys(false);
		if(t == null) {
			GiantBanks.getPlugin().getLogger().log(Level.SEVERE, "Section " + setting + " was not found in the conf.yml! It might be broken...");
		}
		
		for(String i : t) {
			m.put(i, c.get(setting + "." + i));
		}
		
		return m;
	}
	
	public static config Obtain() {
		if(config.instance == null)
			config.instance = new config();
		
		return config.instance;
	}
	
	public static void Kill() {
		if(config.instance != null)
			config.instance = null;
	}
}
