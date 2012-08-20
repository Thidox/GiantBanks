package nl.giantit.minecraft.GiantBanks.core.Updater;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Updater.Database.dbUpdate;

import java.net.URL;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Updater {

	private GiantBanks plugin;
	private int tID;
	private config conf = config.Obtain();
	
	private boolean outOfDate = false;
	private String newVersion = "";
	
	private void start() {
		tID = this.plugin.scheduleAsyncRepeatingTask(new Runnable() {
			@Override
			public void run() {
				newVersion = updateCheck(plugin.getDescription().getVersion());
				if(isNewer(newVersion, plugin.getDescription().getVersion())) {
					outOfDate = true;
					GiantBanks.log.log(Level.WARNING, "[" + plugin.getName() + "] " + newVersion + " has been released! You are currently running: " + plugin.getDescription().getVersion());
					if(conf.getBoolean("GiantBanks.Updater.broadcast"))
						Heraut.broadcast("&cA new version of GiantBanks has just ben released! You are currently running: " + plugin.getDescription().getVersion() + " while the latest version is: " + newVersion, true);
				}
			}
		}, 0L, 432000L);
	}
	
	public Updater(GiantBanks plugin) {
		this.plugin = plugin;
		if(conf.getBoolean("GiantBanks.Updater.checkForUpdates")) {
			this.start();
		}
	}
	
	public void Update(UpdateType t) {
		switch(t) {
			case CONFIG:
				//Config.Update();
				break;
			case DATABASE:
				dbUpdate.Update();
				break;
			case ITEMS:
				//Items.Update();
				break;
			case LANGFILE:
				//Config.Update();
				break;
			case MESSAGETPL:
				//MessageTPL.Update();
				break;
			default:
				break;
		}
	}
	
	public void stop() {
		if(!Double.isNaN(tID)) {
			plugin.getServer().getScheduler().cancelTask(tID);
		}
	}
	
	public String updateCheck(String version) {
		String uri = "http://dev.bukkit.org/server-mods/giantbanks/files.rss";
		try {
			URL url = new URL(uri);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			Node firstNode = doc.getElementsByTagName("item").item(0);
			if(firstNode.getNodeType() == 1) {
				NodeList firstElementTagName = ((Element)firstNode).getElementsByTagName("title");
				NodeList firstNodes = ((Element)firstElementTagName.item(0)).getChildNodes();
				return firstNodes.item(0).getNodeValue().replace("GiantBanks", "").replaceAll(" \\(([a-zA-Z ]+)\\)", "").trim();
			}
		}catch (Exception e) {	
		}
		
		return version;
	}
	
	public boolean isNewer(String newVersion, String version) {
		String[] nv = newVersion.replaceAll("\\.[a-zA-Z]+", "").split("\\.");
		String[] v = version.replaceAll("\\.[a-zA-Z]+", "").split("\\.");
		Boolean isNew = false;
		Boolean prevIsEqual = false; 
		
		for(int i = 0; i < nv.length; i++) {
			int tn = Integer.parseInt(nv[i]);
			int tv = 0;
			if(v.length - 1 >= i)
				tv = Integer.parseInt(v[i]);
			
			if(tn > tv) {
				if(i == 0 || prevIsEqual == true) {
					isNew = true;
					break;
				}
			}else if(tn == tv) {
				prevIsEqual = true;
			}else{
				break;
			}
			
		}

		return isNew;
	}
	
	public Boolean isOutOfDate() {
		return this.outOfDate;
	}
	
	public String getNewVersion() {
		return this.newVersion;
	}
	
}
