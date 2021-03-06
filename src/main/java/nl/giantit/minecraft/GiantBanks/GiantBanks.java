package nl.giantit.minecraft.GiantBanks;

import nl.giantit.minecraft.GiantBanks.Commands.ChatExecutor;
import nl.giantit.minecraft.GiantBanks.Commands.ConsoleExecutor;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Database.Database;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Metrics.MetricsHandler;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.dbHandler;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.dbInit;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.Sync.Sync;
import nl.giantit.minecraft.GiantBanks.core.Updater.Updater;
import nl.giantit.minecraft.GiantBanks.core.perms.PermHandler;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import nl.giantit.minecraft.GiantBanks.Listeners.ServerListener;

public class GiantBanks extends JavaPlugin {

	public static final Logger log = Logger.getLogger("Minecraft");
	
	private static GiantBanks plugin;
	private static Server Server;
	
	private Updater updHandler;
	private Database db;
	private dbHandler dH;
	private Sync sync;
	private PermHandler permHandler;
	private ChatExecutor chat;
	private ConsoleExecutor console;
	private Items itemHandler;
	//private Eco econHandler;
	private Messages msgHandler;
	//private Locationer locHandler;
	private MetricsHandler metrics;
	
	private int tID;
	private String name, dir, pubName;
	private String bName = "Dirty Banker";
	
	private boolean useLoc = false;
	public List<String> cmds;
	
	private void setPlugin() {
		GiantBanks.plugin = this;
	}
	
	public GiantBanks() {
		this.setPlugin();
	}
	
	@Override
	public void onEnable() {
		Server = this.getServer();
		
		this.name = getDescription().getName();
		this.dir = getDataFolder().toString();
		
		File configFile = new File(getDataFolder(), "conf.yml");
		if(!configFile.exists()) {
			getDataFolder().mkdir();
			getDataFolder().setWritable(true);
			getDataFolder().setExecutable(true);
			
			extractDefaultFile("conf.yml");
		}
		
		config conf = config.Obtain(this);
		
		try {
			this.updHandler = new Updater(this); // Dirty fix for NPE
			conf.loadConfig(configFile);
			pubName = conf.getString(this.name + ".global.name");
			
			HashMap<String, String> db = (HashMap<String, String>) conf.getMap(this.name + ".db");
			db.put("debug", conf.getString(this.name + ".global.debug"));
			
			this.db = Database.Obtain(this, null, db);
			this.updHandler = new Updater(this);
			itemHandler = new Items(this);
			
			new dbInit(this);
			
			if(conf.getBoolean(this.name + ".permissions.usePermissions")) {
				permHandler = new PermHandler(this, conf.getString(this.name + ".permissions.Engine"), conf.getBoolean(this.name + ".permissions.opHasPerms"));
			}else{
				permHandler = new PermHandler(this, "NOPERM", true);
			}
			
			this.dH = new dbHandler(this);
			this.sync = new Sync(this);
			
			chat = new ChatExecutor(this);
			console = new ConsoleExecutor(this);
			//econHandler = new Eco(this);
			msgHandler = new Messages(this);
			
			getServer().getPluginManager().registerEvents(new nl.giantit.minecraft.GiantBanks.Listeners.PlayerListener(this), this);
			//getServer().getPluginManager().registerEvents(new ServerListener(this), this);
			
			if(conf.getBoolean(this.name + ".metrics.useMetrics")) {
				this.metrics = new MetricsHandler(this);
			}
		}catch(Exception e) {
			log.log(Level.SEVERE, "[" + this.name + "](" + this.bName + ") Failed to load!");
			if(conf.getBoolean(this.name + ".global.debug")) {
				log.log(Level.INFO, "" + e);
				e.printStackTrace();
			}
			Server.getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		this.sync.stop();
		
		this.db.getEngine().close();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			return console.exec(sender, args);
		}
		
		return chat.exec(sender, args);
	}
	
	public int scheduleAsyncDelayedTask(final Runnable run) {
		return getServer().getScheduler().scheduleAsyncDelayedTask(this, run, 20L);
	}
	
	public int scheduleAsyncDelayedTask(final Runnable run, Long delay) {
		return getServer().getScheduler().scheduleAsyncDelayedTask(this, run, delay);
	}
	
	public int scheduleAsyncRepeatingTask(final Runnable run, Long init, Long delay) {
		return getServer().getScheduler().scheduleAsyncRepeatingTask(this, run, init, delay);
	}
	
	public String getPubName() {
		return this.pubName;
	}
	
	public String getDir() {
		return this.dir;
	}
	
	public String getSeparator() {
		return File.separator;
	}
	
	public Boolean useLocation() {
		return this.useLoc;
	}
	
	public Updater getUpdater() {
		return this.updHandler;
	}
	public Boolean isOutOfDate() {
		return this.updHandler.isOutOfDate();
	}
	
	public String getNewVersion() {
		return this.updHandler.getNewVersion();
	}
	
	public Database getDB() {
		return this.db;
	}
	
	public Permission getPermHandler() {
		return this.permHandler.getEngine();
	}
	
	public PermHandler getPermHandler(Boolean obj) {
		return this.permHandler;
	}
	
	public dbHandler getDbHandler() {
		return this.dH;
	}
	
	public Sync getSync() {
		return this.sync;
	}
	
	public Items getItemHandler() {
		return this.itemHandler;
	}
	
	/*public Eco getEcoHandler() {
		return this.econHandler;
	}*/
	
	public Messages getMsgHandler() {
		return this.msgHandler;
	}
	
	/*public Locationer getLocHandler() {
		return this.locHandler;
	}*/
	
	public void extract(String file) {
		extractDefaultFile(file);
	}
	
	public void extract(File file, InputStream input) {
		extractDefaultFile(file, input);
	}
	
	public static GiantBanks getPlugin() {
		return GiantBanks.plugin;
	}
	
	private void extractDefaultFile(String file) {
		File configFile = new File(getDataFolder(), file);
		if (!configFile.exists()) {
			InputStream input = this.getClass().getResourceAsStream("/nl/giantit/minecraft/" + name + "/core/Default/" + file);
			if (input != null) {
				FileOutputStream output = null;

				try {
					output = new FileOutputStream(configFile);
					byte[] buf = new byte[8192];
					int length = 0;

					while ((length = input.read(buf)) > 0) {
						output.write(buf, 0, length);
					}

					log.log(Level.INFO, "[" + name + "] copied default file: " + file);
				} catch (Exception e) {
					Server.getPluginManager().disablePlugin(this);
					log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Can't extract the requested file!!", e);
					return;
				} finally {
					try {
						if (input != null) {
							input.close();
						}
					} catch (Exception e) {
						Server.getPluginManager().disablePlugin(this);
						log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Severe error!!", e);	
					}
					try {
						if (output != null) {
							output.close();
						}
					} catch (Exception e) {
						Server.getPluginManager().disablePlugin(this);
						log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Severe error!!", e);
					}
				}
			}
		}
	}
	
	private void extractDefaultFile(File file, InputStream input) {
		if (!file.exists()) {
			try {
			 file.createNewFile();
			}catch(IOException e) {
				log.log(Level.SEVERE, "[" + name + "] Can't extract the requested file!!", e);
			}
		}
		if (input != null) {
			FileOutputStream output = null;

			try {
				output = new FileOutputStream(file);
				byte[] buf = new byte[8192];
				int length = 0;

				while ((length = input.read(buf)) > 0) {
					output.write(buf, 0, length);
				}

				log.log(Level.INFO, "[" + name + "] copied default file: " + file);
			} catch (Exception e) {
				Server.getPluginManager().disablePlugin(this);
				log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Can't extract the requested file!!", e);
				return;
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (Exception e) {
					Server.getPluginManager().disablePlugin(this);
					log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Severe error!!", e);	
				}
				try {
					if (output != null) {
						output.close();
					}
				} catch (Exception e) {
					Server.getPluginManager().disablePlugin(this);
					log.log(Level.SEVERE, "[" + name + "] AAAAAAH!!! Severe error!!", e);
				}
			}
		}
	}
}
