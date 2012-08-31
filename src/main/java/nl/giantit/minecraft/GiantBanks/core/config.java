package nl.giantit.minecraft.GiantBanks.core;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Updater.UpdateType;
import nl.giantit.minecraft.GiantBanks.core.Updater.Config.confUpdate;

import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
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

	private GiantBanks plugin;
	private YamlConfiguration c;
	private File file;
	private double version = 0.2;
	
	private config(GiantBanks p) {
		this.plugin = p;
	}
	
	public void loadConfig(File file) {
		this.file = file;
		this.c = YamlConfiguration.loadConfiguration(this.file);
		
		double v = this.getDouble(plugin.getName() + ".global.version");
		if(v < this.version) {
			confUpdate cU = (confUpdate) plugin.getUpdater().getUpdater(UpdateType.CONFIG);
			File oconfigFile = new File(plugin.getDir(), "conf.yml." + v + ".bak");
			try {
				Files.copy(file, oconfigFile);
				cU.Update(v, c);
				
				this.c = YamlConfiguration.loadConfiguration(new File(plugin.getDir(), "conf.yml"));
			}catch(IOException e) {
				plugin.getLogger().severe("Failed to update config file!");
				if(c.getBoolean("GiantShop.global.debug", true) == true) {
					e.printStackTrace();
				}
			}
			
			//plugin.extract("conf.yml");
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
	
	public Boolean getBoolean(String setting, Boolean b) {
		return null != this.c ? this.c.getBoolean(setting, b) : b;
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
			plugin.getLogger().log(Level.SEVERE, "Section " + setting + " was not found in the conf.yml! It might be broken...");
		}
		
		for(String i : t) {
			m.put(i, c.get(setting + "." + i));
		}
		
		return m;
	}
	
	public static config Obtain(GiantBanks p) {
		if(config.instance == null)
			config.instance = new config(p);
		
		return config.instance;
	}
	
	public static config Obtain() {
		if(config.instance == null)
			config.instance = new config(GiantBanks.getPlugin());
		
		return config.instance;
	}
	
	public static void Kill() {
		if(config.instance != null)
			config.instance = null;
	}
}
