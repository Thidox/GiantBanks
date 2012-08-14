package nl.giantit.minecraft.GiantBanks.Commands.Chat;

import nl.giantit.minecraft.GiantBanks.GiantBanks;

import org.bukkit.entity.Player;

public class Have {

	public static void exec(Player p, String[] args) {
		p.sendMessage("test");
		GiantBanks.getPlugin().getLogger().warning("test");
	}
}
