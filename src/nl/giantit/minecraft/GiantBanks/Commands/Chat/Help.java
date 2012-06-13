package nl.giantit.minecraft.GiantBanks.Commands.Chat;

import nl.giantit.minecraft.GiantBanks.core.config;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Help {

	private static ArrayList<String[]> entries = new ArrayList<String[]>();
	private static config conf = config.Obtain();
	//private static perm perms = perm.Obtain();
	
	private static void init() {
		entries = new ArrayList<String[]>();
		entries.add(new String[] {"shop", "Show GiantBanks help page 1", "null"});
		entries.add(new String[] {"shop help|h|? (page)", "Show GiantBanks help page x", "null"});
		entries.add(new String[] {"shop sendhelp|sh [receiver] (page)", "Send GiantBanks help page x to player y", "giantbanks.admin.sendhelp"});
		entries.add(new String[] {"shop list|l (page)", "Show all items in the shop", "giantbanks.shop.list"});
	}
	
	public static void showHelp(Player player, String[] args) {
		if(entries.isEmpty())
			init();
		
		ArrayList<String[]> uEntries = new ArrayList<String[]>();
		for(int i = 0; i < entries.size(); i++) {
			String[] data = entries.get(i);

			String permission = data[2];

			/*if(permission.equalsIgnoreCase("null") || pH.has((Player)player, (String)permission)) {
				uEntries.add(data);				
			}else{
				continue;
			}*/
		}
	}
	
}
