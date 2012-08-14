package nl.giantit.minecraft.GiantBanks.Commands.Chat.UserAccount;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.UserAccount;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Clear {

	private static Permission pH = GiantBanks.getPlugin().getPermHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();
	
	public static void exec(Player p, String[] args) {
		if(pH.has(p, "giantbanks.admin.account.clear")) {
			if(sH.contains(p.getName() + ".selectedAccount")) {
				UserAccount uA = (UserAccount) sH.get(p.getName() + ".selectedAccount");
				uA.eraseAll();
				
				HashMap<String, String> d = new HashMap<String, String>();
				d.put("id", String.valueOf(uA.getAccountID()));
				d.put("acc", uA.getOwner());
				
				Heraut.say(p, mH.getMsg(msgType.ADMIN, "accCleared", d));
			}else{
				Heraut.say(p, mH.getMsg(msgType.ERROR, "noAccountSelected"));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "account clear");
			
			Heraut.say(p, mH.getMsg(msgType.ERROR, "noPermissions", d));
		}
	}
}
