package nl.giantit.minecraft.GiantBanks.Commands.Console;

import nl.giantit.minecraft.GiantBanks.core.config;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class Help {

	private static ArrayList<String[]> entries = new ArrayList<String[]>();
	private static config conf = config.Obtain();
	
	private static void init() {
		entries = new ArrayList<String[]>();
		entries.add(new String[] {"shop", "Show GiantBanks help page 1"});
		entries.add(new String[] {"shop help|h|? (page)", "Show GiantBanks help page x"});
		entries.add(new String[] {"shop sendhelp|sh [receiver] (page)", "Send GiantBanks help page x to player y"});
		entries.add(new String[] {"shop list|l (page)", "Show all known accounts"});
	}
	
	public static void showHelp(CommandSender sender, String[] args) {
		if(entries.isEmpty())
			init();
		
		ArrayList<String[]> uEntries = new ArrayList<String[]>();
		for(int i = 0; i < entries.size(); i++) {
			String[] data = entries.get(i);

			/*if(permission.equalsIgnoreCase("null") || pH.has((Player)player, (String)permission)) {
				uEntries.add(data);				
			}else{
				continue;
			}*/
		}
	}
	
}
