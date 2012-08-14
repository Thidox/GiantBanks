package nl.giantit.minecraft.GiantBanks.Commands.Chat.UserAccount;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.UserAccount;
import nl.giantit.minecraft.GiantBanks.core.config;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Has {

	private static config conf = config.Obtain();
	private static Permission pH = GiantBanks.getPlugin().getPermHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();
	
	public static void exec(Player p, String[] args) {
		if(pH.has(p, "giantbanks.admin.account.has")) {
			if(sH.contains(p.getName() + ".selectedAccount")) {
				UserAccount uA = (UserAccount) sH.get(p.getName() + ".selectedAccount");
				HashMap<String, Integer> slots = uA.getItemList();
				
				if(slots.size() > 0) {
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
					
					int pages = ((int)Math.ceil((double)slots.size() / (double)perPage) < 1) ? 1 : (int)Math.ceil((double)slots.size() / (double)perPage);
					int start = (curPag * perPage) - perPage;
					
					if(curPag > pages) {
						HashMap<String, String> d = new HashMap<String, String>();
						d.put("page", String.valueOf(curPag));
						d.put("maxPages", String.valueOf(pages));
						
						Heraut.say(p, mH.getMsg(msgType.ERROR, "pageExceedsMax", d));
						return;
					}
					
					HashMap<String, String> d = new HashMap<String, String>();
					d.put("aID", String.valueOf(uA.getAccountID()));
					d.put("acc", uA.getOwner());
					d.put("page", String.valueOf(curPag));
					d.put("maxPages", String.valueOf(pages));
					
					Heraut.say(p, mH.getMsg(msgType.ADMIN, "accHolds", d));
					Heraut.say(p, mH.getMsg(msgType.MAIN, "page", d));
					
					int i = 0;
					for(Map.Entry<String, Integer> slot : slots.entrySet()) {
						if(i < start) {
							i++;
							continue;
						}
			
						i++;
						
						if(i - start > perPage)
							break;
						
						d = new HashMap<String, String>();
						d.put("item", slot.getKey());
						d.put("amt", String.valueOf(slot.getValue()));
						
						Heraut.say(p, mH.getMsg(msgType.MAIN, "accHoldItem", d));
					}
				}else{
					Heraut.say(p, mH.getMsg(msgType.ERROR, "noStoredItems"));
				}
			}else{
				Heraut.say(p, mH.getMsg(msgType.ERROR, "noAccountSelected"));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "account has");
			
			Heraut.say(p, mH.getMsg(msgType.ERROR, "noPermissions", d));
		}
	}
}
