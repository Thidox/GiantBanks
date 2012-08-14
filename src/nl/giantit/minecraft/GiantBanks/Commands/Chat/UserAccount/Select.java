package nl.giantit.minecraft.GiantBanks.Commands.Chat.UserAccount;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.UserAccount;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Select {

	private static Permission pH = GiantBanks.getPlugin().getPermHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();
	
	public static void exec(Player p, String[] args) {
		if(args.length > 2) {
			if(pH.has(p, "giantbanks.admin.account.select")) {
				OfflinePlayer op = Misc.getPlayer(args[2]);
				UserAccount uA;
				if(null != op) {
					uA = UserAccount.getUserAccount(op.getName());
				}else{
					uA = UserAccount.getUserAccount(args[2]);
				}
				
				if(null != uA) {
					if(sH.contains(p.getName() + ".selectedAccount"))
						sH.remove(p.getName() + ".selectedAccount");
					
					if(sH.store(p.getName() + ".selectedAccount", uA)) {
						HashMap<String, String> d = new HashMap<String, String>();
						d.put("id", String.valueOf(uA.getAccountID()));
						d.put("acc", uA.getOwner());
						
						Heraut.say(p, mH.getMsg(msgType.ADMIN, "accountSelected", d));
					}else{
						Heraut.say(p, mH.getMsg(msgType.ERROR, "unknown"));
					}
				}else{
					HashMap<String, String> d = new HashMap<String, String>();
					d.put("acc", args[2]);
					
					Heraut.say(p, mH.getMsg(msgType.ERROR, "accountNotFound", d));
				}
			}else{
				HashMap<String, String> d = new HashMap<String, String>();
				d.put("command", "type account select");
				
				Heraut.say(p, mH.getMsg(msgType.ERROR, "noPermissions", d));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "type account select");
			
			Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
		}
	}
}
