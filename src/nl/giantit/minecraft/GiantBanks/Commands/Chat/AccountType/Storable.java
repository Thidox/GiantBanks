package nl.giantit.minecraft.GiantBanks.Commands.Chat.AccountType;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Storable {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();
	
	public static void exec(Player p, String[] args) {
		if(args.length > 3) {
			if(sH.contains(p.getName() + ".selectedType")) {
				String item = null;
				int id = 0;
				Integer type = null;
				Boolean allow = false;
				
				for(int i = 1; i < args.length; i++) {
					if(args[i].equalsIgnoreCase("-i")) {
						i++;
						if(args.length - 1 >= i) {
							item = args[i];
							continue;
						}else{
							break;
						}
					}else if(args[i].startsWith("-i:")) {
						item = args[i].replaceFirst("-i:", "");
						continue;
					}else if(args[i].equalsIgnoreCase("-id")) {
						i++;
						if(args.length - 1 >= i) {
							try{
								id = Integer.parseInt(args[i]);
							}catch(NumberFormatException e) {
								//ignore
							}
							continue;
						}else{
							break;
						}
					}else if(args[i].startsWith("-id:")) {
						try{
							id = Integer.parseInt(args[i].replaceFirst("-id:", ""));
						}catch(NumberFormatException e) {
							//ignore
						}
						continue;
					}else if(args[i].equalsIgnoreCase("-t")) {
						i++;
						if(args.length - 1 >= i) {
							try{
								type = Integer.parseInt(args[i]);
							}catch(NumberFormatException e) {
								//ignore
							}
							continue;
						}else{
							break;
						}
					}else if(args[i].startsWith("-t:")) {
						try{
							type = Integer.parseInt(args[i].replaceFirst("-t:", ""));
						}catch(NumberFormatException e) {
							//ignore
						}
						continue;
					} if(args[i].equalsIgnoreCase("-a")) {
						i++;
						if(args.length - 1 >= i) {
							String s = args[i];
							if(Misc.isAnyIgnoreCase(s, "1", "y", "yes", "yea", "allow", "a", "true")) {
								allow = true;
							}
							continue;
						}else{
							break;
						}
					}else if(args[i].startsWith("-a:")) {
						String s = args[i].replaceFirst("-a:", "");
						if(Misc.isAnyIgnoreCase(s, "1", "y", "yes", "yea", "allow", "a", "true")) {
							allow = true;
						}
						continue;
					}
				}
				
				if(item == null) {
					if(id > 0) {
						if(null != type && 0 == type)
							type = null;
						if(iH.isValidItem(id, type)) {
							item = iH.getItemNameByID(id, type);
						}else{
							Heraut.say(p, mH.getMsg(msgType.ERROR, "itemInvalid"));
							return;
						}
					}else{
						Heraut.say(p, mH.getMsg(msgType.ERROR, "itemIDInvalid"));
						return;
					}
				}
				
				if(item != null) {
					AccountType aT = (AccountType) sH.get(p.getName() + ".selectedType");
					int status = aT.setStorable(allow, item);
					HashMap<String, String> d = new HashMap<String, String>();
					switch(status) {
						case 0:
							d.put("allow", (allow ? "allowed" : "dissallowed"));
							d.put("item", item);
							d.put("type", aT.getName());
							Heraut.say(p, mH.getMsg(msgType.ADMIN, "storableSet", d));
							break;
						case -1:
							Heraut.say(p, mH.getMsg(msgType.ERROR, "ItemInvalid"));
							break;
						case -2:
							d.put("allow", (allow ? "allow" : "dissallow"));
							d.put("item", item);
							d.put("type", aT.getName());
							Heraut.say(p, mH.getMsg(msgType.ERROR, "storableFail", d));
							break;
					}
					return;
				}else{
					Heraut.say(p, mH.getMsg(msgType.ERROR, "itemInvalid"));
					return;
				}
			}else{
				Heraut.say(p, mH.getMsg(msgType.ERROR, "noTypeSelected"));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "type storable add");
			
			Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
		}
	}
}
