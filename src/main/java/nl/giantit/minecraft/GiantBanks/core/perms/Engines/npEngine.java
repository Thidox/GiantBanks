package nl.giantit.minecraft.GiantBanks.core.perms.Engines;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.entity.Player;

import java.util.logging.Level;

public class npEngine implements Permission {

	private GiantBanks plugin;
	private Boolean opHasPerms;
	
	public npEngine(GiantBanks plugin, Boolean opHasPerms) {
		this.plugin = plugin;
		this.opHasPerms = opHasPerms;
		
		plugin.getLogger().log(Level.INFO, "Not using any permissions!");
		plugin.getLogger().log(Level.WARNING, "No permissions has no group support!");
	}
	
	@Override
	public boolean has(String p, String perm) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.has(player, perm);
		
		return false;
	}

	@Override
	public boolean has(Player p, String perm) {
		if(opHasPerms && p.isOp())
			return true;
		
		return false;
	}
	
	@Override
	public boolean has(String p, String perm, String world) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.has(player, perm, world);
		
		return false;
	}

	@Override
	public boolean has(Player p, String perm, String world) {
		if(opHasPerms && p.isOp())
			return true;
		
		return false;
	}

	@Override
	public boolean groupHasPerm(String group, String perm) {
		return false;
	}

	@Override
	public boolean groupHasPerm(String group, String perm, String world) {
		return false;
	}

	@Override
	public boolean inGroup(String p, String group) {
		Player player = plugin.getServer().getPlayer(p);
		if(player != null)
			return this.inGroup(player, group);
		
		return false;
	}

	@Override
	public boolean inGroup(Player p, String group) {
		return false;
	}
	
	@Override
	public boolean inGroup(String p, String group, String world) {
		return false;
	}

	@Override
	public boolean inGroup(Player p, String group, String world) {
		return this.inGroup(p.getName(), group, world);
	}

	@Override
	public String getGroup(String p) {
		return null;
	}

	@Override
	public String getGroup(Player p) {
		return this.getGroup(p.getName());
	}

	@Override
	public String getGroup(String p, String world) {
		return null;
	}

	@Override
	public String getGroup(Player p, String world) {
		return this.getGroup(p.getName(), world);
	}

	@Override
	public String[] getGroups(String p) {
		return null;
	}

	@Override
	public String[] getGroups(Player p) {
		return this.getGroups(p.getName());
	}

	@Override
	public String[] getGroups(String p, String world) {
		return null;
	}

	@Override
	public String[] getGroups(Player p, String world) {
		return this.getGroups(p.getName(), world);
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}
