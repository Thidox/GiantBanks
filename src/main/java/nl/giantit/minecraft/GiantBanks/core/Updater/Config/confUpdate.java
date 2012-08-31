package nl.giantit.minecraft.GiantBanks.core.Updater.Config;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Updater.iUpdater;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class confUpdate implements iUpdater {
	
	private void export(File file, FileConfiguration c) {
		try {
			InputStream iS = new ByteArrayInputStream(c.saveToString().replace("\n", "\r\n").replace("  ", "    ").getBytes("UTF-8"));
			GiantBanks.getPlugin().extract(file, iS);
		}catch(UnsupportedEncodingException e) {
			GiantBanks.getPlugin().getLogger().severe("Failed to update config file!");
			if(c.getBoolean("GiantShop.global.debug", true) == true) {
				e.printStackTrace();
			}
		}
	}

	private void update0_2(FileConfiguration c) {
		Map<String, Boolean> section = new HashMap<String, Boolean>();
		section.put("creation", true);
		section.put("modication", true);
		section.put("removal", true);
		c.createSection("GiantBanks.log.log.accounttype", section);
		
		section = new HashMap<String, Boolean>();
		section.put("creation", true);
		section.put("modication", true);
		section.put("removal", true);
		c.createSection("GiantBanks.log.log.useraccount", section);
		
		section = new HashMap<String, Boolean>();
		section.put("storing", true);
		section.put("taking", true);
		c.createSection("GiantBanks.log.log.useraccount.item", section);
		
		ConfigurationSection cS = c.createSection("GiantBanks.Updater");
		cS.set("checkForUpdates", c.get("GiantBanks.global.debug"));
		cS.set("broadcast", true);
		
		c.set("GiantBanks.global.version", 0.2);
		
		this.export(new File(GiantBanks.getPlugin().getDir(), "conf.yml"), c);
	}
	
	public void Update(double curV, FileConfiguration c) {
		if(curV < 0.2) {
			GiantBanks.getPlugin().getLogger().log(Level.INFO, "Your conf.yml has ran out of date. Updating to 0.2 now!");
			update0_2(c);
		}
	}
	
}
