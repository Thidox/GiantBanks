package nl.giantit.minecraft.GiantBanks.Commands.Chat;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Help {

	private static ArrayList<String[]> entries = new ArrayList<String[]>();
	private static config conf = config.Obtain();
	private static Permission pH = GiantBanks.getPlugin().getPermHandler(); 
	
	private static void init() {
		entries = new ArrayList<String[]>();
		entries.add(new String[] {"bank", "Show page 1 of items in your account", "giantbanks.bank.have"});
		entries.add(new String[] {"bank help|? (page)", "Show GiantBanks help page x", "null"});
		entries.add(new String[] {"bank sendhelp|sh [receiver] (page)", "Send GiantBanks help page x to player y", "giantbanks.admin.sendhelp"});
		entries.add(new String[] {"bank store|s (-id [id]) (-t [type]) (-i [item]) -a [amount]", "Store an item into your bank account", "giantbanks.bank.store"});
		entries.add(new String[] {"bank get|g (-id [id]) (-t [type]) (-i [item]) -a [amount]", "Get an item from your bank account", "giantbanks.bank.get"});
		entries.add(new String[] {"bank getall|ga (-id [id]) (-t [type]) (-i [item])", "Get the entire quantity of an item from your bank account", "giantbanks.bank.getall"});
		entries.add(new String[] {"bank have|h (page)", "Show page x of items in your account", "giantbanks.bank.have"});
		entries.add(new String[] {"bank type|t create|c -n [name] (-ms [maxSlots]) (-mps [maxPerSlot])", "Create a new account type with the name x", "giantbanks.admin.type.create"});
		entries.add(new String[] {"bank type|t update|u select|s [type]", "Select an account type for further modifications", "giantbanks.admin.type.select"});
		entries.add(new String[] {"bank type|t update|u set [property] [value]", "Set property x to value y for selected account type", "giantbanks.admin.type.set"});
		entries.add(new String[] {"bank type|t storable|s (-id [id]) (-t [type]) (-i [item]) -a [allow]", "Allow item to be stored in selected account type", "giantbanks.admin.type.storable"});
		entries.add(new String[] {"bank account|a select|s [account]", "Select a user account for further modifications", "giantbanks.admin.account.select"});
		entries.add(new String[] {"bank account|a type|t [type]", "Set the account type for the selected user account", "giantbanks.admin.account.settype"});
		entries.add(new String[] {"bank account|a has", "Check items in the selected user account", "giantbanks.admin.account.has"});
		entries.add(new String[] {"bank account|a clear|c", "Erase all items in the selected user account", "giantbanks.admin.account.clear"});
	}
	
	public static void showHelp(Player player, String[] args) {
		if(entries.isEmpty())
			init();
		
		ArrayList<String[]> uEntries = new ArrayList<String[]>();
		for(int i = 0; i < entries.size(); i++) {
			String[] data = entries.get(i);

			String permission = data[2];

			if(permission.equalsIgnoreCase("null") || pH.has((Player)player, (String)permission)) {
				uEntries.add(data);				
			}else{
				continue;
			}
		}
		
		String name = GiantBanks.getPlugin().getPubName();
		int perPage = conf.getInt("GiantBanks.global.perPage");
		int curPag = 0;
		
		if(args.length >= 2) {
			try{
				curPag = Integer.parseInt(args[1]);
			}catch(Exception e) {
				curPag = 1;
			}
		}else
			curPag = 1;
		
		curPag = (curPag > 0) ? curPag : 1;
		
		int pages = ((int)Math.ceil((double)uEntries.size() / (double)perPage) < 1) ? 1 : (int)Math.ceil((double)uEntries.size() / (double)perPage);
		int start = (curPag * perPage) - perPage;
		
		if(uEntries.size() <= 0) {
			Heraut.say(player, "&e[&3" + name + "&e]&c Sorry no help entries yet :(");
		}else if(curPag > pages) {
			Heraut.say(player, "&e[&3" + name + "&e]&c My help list only has &e" + pages + " &cpages!!");
		}else{
			Heraut.say(player, "&e[&3" + name + "&e]&f Help. Page: &e" + curPag + "&f/&e" + pages);

			for(int i = start; i < (((start + perPage) > uEntries.size()) ? uEntries.size() : (start + perPage)); i++) {
				String[] data = uEntries.get(i);

				String helpEntry = data[0];
				String description = data[1];
				Messages msg = GiantBanks.getPlugin().getMsgHandler();
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("command", helpEntry);
				params.put("description", description);
				
				Heraut.say(player, msg.getMsg(Messages.msgType.MAIN, "helpCommand", params));
			}
		}
	}
	
	public static void sendHelp(Player player, String[] args) {
		if(entries.isEmpty())
			init();
		
		String name = conf.getString("GiantBanks.global.name");
		int perPage = conf.getInt("GiantBanks.global.perPage");
		int curPag = 0;
		
		String usr;
		
		if(args.length >= 2) {
			usr = args[1];
			if(args.length >= 3) {
				try{
					curPag = Integer.parseInt(args[2]);
				}catch(Exception e) {
					curPag = 1;
				}
			}else
				curPag = 1;
		}else{
			curPag = 1;
			usr = null;
		}
		
		if(usr != null) {
			Player receiver = GiantBanks.getPlugin().getServer().getPlayer(usr);
			if(receiver != null && receiver.isOnline()) {
				
				ArrayList<String[]> uEntries = new ArrayList<String[]>();
				for(int i = 0; i < entries.size(); i++) {
					String[] data = entries.get(i);

					String permission = data[2];

					if(permission.equalsIgnoreCase("null") || pH.has(receiver, (String)permission)) {
						uEntries.add(data);				
					}else{
						continue;
					}
				}
				curPag = (curPag > 0) ? curPag : 1;
				
				int pages = ((int)Math.ceil((double)uEntries.size() / (double)perPage) < 1) ? 1 : (int)Math.ceil((double)uEntries.size() / (double)perPage);
				int start = (curPag * perPage) - perPage;

				if(uEntries.size() <= 0) {
					Heraut.say(player, "&e[&3" + name + "&e]&c Sorry no help entries yet :(");
					return;
				}else if(curPag > pages) {
					Heraut.say(player, "&e[&3" + name + "&e]&c My help list for player " + usr + " only has &e" + pages + " &cpages!!");
					return;
				}else{
					if(!pH.has(player, "giantbanks.admin.sendhelp")) {
						Heraut.say(player, "&cYou have no access to that command!");
						return;
					}
					Heraut.say(player, "&e[&3" + name + "&e]&f Sending help page &e" + curPag + "&f to player &e" + usr);
					Heraut.say(receiver, "&e[&3" + name + "&e]&f You were sent help by " + player.getDisplayName() + "!");
					Heraut.say(receiver, "&e[&3" + name + "&e]&f Help. Page: &e" + curPag + "&f/&e" + pages);

					for(int i = start; i < (((start + perPage) > uEntries.size()) ? uEntries.size() : (start + perPage)); i++) {
						String[] data = uEntries.get(i);

						String helpEntry = data[0];
						String description = data[1];

						Heraut.say(receiver, "&c/" + helpEntry + " &e-&f " + description);
					}
				}
			}else{
				Heraut.say(player, "&e[&3" + name + "&e]&c The requested player does not seem to be online or even not existing! :(");
			}
		}else{
			Help.showHelp(player, args);
		}
	}
	
}
