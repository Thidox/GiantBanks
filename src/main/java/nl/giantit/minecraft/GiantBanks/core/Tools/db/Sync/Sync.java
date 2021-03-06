package nl.giantit.minecraft.GiantBanks.core.Tools.db.Sync;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.dbHandler;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.dbType;

public class Sync {
	
	private GiantBanks plugin;
	private int tID;
	private config conf = config.Obtain();
	private Boolean accSave = false;
	private Boolean typeSave = false;
	private Boolean bankSave = false;
	private Boolean logSave = false;
	private Register sH = Register.getInstance();
	
	private Long getDelayTime() {
		Long delay = 18000L;
		String time = conf.getString("GiantBanks.cache.updateDelay");
		try {
			if(time.contains(":")) {
				String[] parts = time.split(":");
				if(parts.length >= 3) {
					delay = Long.parseLong(parts[0]) * 60 * 60 * 20;
					delay += Long.parseLong(parts[1]) * 60 * 20;
					delay += Long.parseLong(parts[2]) * 20;
				}else if(parts.length == 2) {
					delay = Long.parseLong(parts[0]) * 60 * 20;
					delay += Long.parseLong(parts[1]) * 20;
				}else{
					delay = Long.parseLong(parts[0]) * 20;
				}
			}else{
				delay = Long.parseLong(time) * 60 * 20;
			}
			
			delay = (delay < 600L) ? 18000L : delay;
		}catch(NumberFormatException e) {
			delay = 18000L;
		}
		
		return delay;
	}
	
	private void init() {
		dbHandler dH = plugin.getDbHandler();
		dH.init(null);
	}
	
	private void start() {
		Long delay = this.getDelayTime();
		tID = this.plugin.scheduleAsyncRepeatingTask(
				new Runnable() {
					@Override
					public void run() {
						dbHandler dH = plugin.getDbHandler();
						dH.save(null);
						
						sH.remove("loggedOffPlayers");
					}
				}, delay, delay);
	}
	
	private void shutOff() {
		dbHandler dH = plugin.getDbHandler();
		dH.fullSave(null);
	}
	
	public Sync(GiantBanks plugin) {
		this.plugin = plugin;
		this.init();
		
		if(conf.getBoolean("GiantBanks.cache.useCache"))
			this.start();
	}
	
	public void saveNow() {
		dbHandler dH = plugin.getDbHandler();
		dH.save(null);
	}
	
	public void saveNow(dbType t) {
		dbHandler dH = plugin.getDbHandler();
		dH.save(t);
	}
	
	private void fullSave() {
		dbHandler dH = plugin.getDbHandler();
		dH.fullSave(null);
	}
	
	private void fullSave(dbType t) {
		dbHandler dH = plugin.getDbHandler();
		dH.fullSave(t);
	}
	
	public void callUpdate(final dbType t) {
		final dbHandler dH = plugin.getDbHandler();
		switch(t) {
			case ACCOUNTS:
				if(!conf.getBoolean("GiantBanks.cache.cache.accounts")) {
					if(!this.accSave) {
						this.accSave = true;
						this.plugin.scheduleAsyncDelayedTask(new Runnable() {
							@Override
							public void run() {
								dH.save(t);
								accSave = false;
								
								sH.remove("loggedOffPlayers");
							}
						}, 5L);
					}
				}
				break;
			case TYPES:
				if(!conf.getBoolean("GiantBanks.cache.cache.types"))
					if(!this.typeSave) {
						this.typeSave = true;
						this.plugin.scheduleAsyncDelayedTask(new Runnable() {
							@Override
							public void run() {
								dH.save(t);
								typeSave = false;
							}
						}, 5L);
					}
				break;
			case BANKS:
				if(!conf.getBoolean("GiantBanks.cache.cache.banks"))
					if(!this.bankSave) {
						this.bankSave = true;
						this.plugin.scheduleAsyncDelayedTask(new Runnable() {
							@Override
							public void run() {
								dH.save(t);
								bankSave = false;
							}
						}, 5L);
					}
				break;
			case LOGS:
				if(!this.logSave) {
					this.logSave = true;
					this.plugin.scheduleAsyncDelayedTask(new Runnable() {
						@Override
						public void run() {
							dH.save(t);
							logSave = false;
						}
					}, 5L);
				}
				break;
			default:
				break;
		}
	}
	
	public void stop() {
		if(!Double.isNaN(tID)) {
			plugin.getServer().getScheduler().cancelTask(tID);
		}
		
		this.saveNow();
	}
}
