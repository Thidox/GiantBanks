package nl.giantit.minecraft.GiantBanks.Listeners;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.UserAccount;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.dbType;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.Handler.Accounts;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {
	
	private GiantBanks plugin;
	private config conf = config.Obtain();
	private Register sH = Register.getInstance();
	private Permission pH;
	
	public PlayerListener(GiantBanks plugin) {
		this.plugin = plugin;
		pH = plugin.getPermHandler();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent e) {
		if(e.getPlayer().isOp()) {
			if(plugin.isOutOfDate() && conf.getBoolean("GiantBanks.Updater.broadcast")) {
				plugin.scheduleAsyncDelayedTask(new Runnable() {
					@Override
					public void run()
					{
						delayedJoin(e.getPlayer());
					}
				});
			}
		}
		
		plugin.scheduleAsyncDelayedTask(new Runnable() {
			@Override
			public void run() {
				buildAccount(e.getPlayer());
			}
		});
	}
	

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(final PlayerQuitEvent e) {
		plugin.scheduleAsyncDelayedTask(new Runnable() {
			@Override
			public void run() {
				if(!sH.contains("loggedOffPlayers"))
					sH.store("loggedOffPlayers", new ArrayList<String>());
				
				List<String> oP = (List<String>) sH.get("loggedOffPlayers");
				oP.add(e.getPlayer().getName());
				sH.store("loggedOffPlayers", oP, true);
			}
		});
	}
	
	public void delayedJoin(Player p) {
		Heraut.say(p.getPlayer(), "&cA new version of GiantBanks has been released! You are currently running: " + plugin.getDescription().getVersion() + " while the latest version is: " + plugin.getNewVersion());
	}
	
	public void buildAccount(Player p) {
		if(!sH.contains("loggedOffPlayers"))
			sH.store("loggedOffPlayers", new ArrayList<String>());
		
		UserAccount uA = UserAccount.getUserAccount(p.getName());
		if(null != uA) {
			List<String> oP = (List<String>) sH.get("loggedOffPlayers");
			if(!oP.contains(p.getName()) || !uA.isUpdated()) {
				Accounts aH = (Accounts) plugin.getDbHandler().getHandler(dbType.ACCOUNTS);
				aH.load(p.getName());
			}
		}else{
			if(pH.has(p, "giantbanks.bank.have")) {
				UserAccount.createUserAccount(p.getName());
			}
		}
	}
}
