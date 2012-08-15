package nl.giantit.minecraft.GiantBanks.Listeners;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
	
	private GiantBanks plugin;
	private config conf = config.Obtain();
	
	public PlayerListener(GiantBanks plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
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
	}
	
	public void delayedJoin(Player p) {
		Heraut.say(p.getPlayer(), "&cA new version of GiantBanks has been released! You are currently running: " + plugin.getDescription().getVersion() + " while the latest version is: " + plugin.getNewVersion());
	}

}
