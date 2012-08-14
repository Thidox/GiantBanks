package nl.giantit.minecraft.GiantBanks.Commands.Chat.UserAccount;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.Bank.UserAccount;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetType {

	private static Permission pH = GiantBanks.getPlugin().getPermHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();
	
	public static void exec(Player p, String[] args) {
		if(args.length >= 2) {
			if(pH.has(p, "giantbanks.admin.account.settype")) {
				if(sH.contains(p.getName() + ".selectedAccount")) {
					AccountType aT;
					try{
						aT = AccountType.getType(Integer.parseInt(args[2]));
					}catch(NumberFormatException e) {
						aT = AccountType.getType(args[2]);
					}
					
					if(null != aT) {
						UserAccount uA = (UserAccount) sH.get(p.getName() + ".selectedAccount");
						uA.setType(aT);
						HashMap<String, String> d = new HashMap<String, String>();
						d.put("id", String.valueOf(uA.getAccountID()));
						d.put("acc", uA.getOwner());
						d.put("tid", String.valueOf(aT.getTypeID()));
						d.put("type", aT.getName());
						
						Heraut.say(p, mH.getMsg(msgType.ADMIN, "typeChanged", d));
					}else{
						HashMap<String, String> d = new HashMap<String, String>();
						d.put("type", args[2]);
						
						Heraut.say(p, mH.getMsg(msgType.ERROR, "typeNotFound", d));
					}
				}else{
					Heraut.say(p, mH.getMsg(msgType.ERROR, "noAccountSelected"));
				}
			}else{
				HashMap<String, String> d = new HashMap<String, String>();
				d.put("command", "account type");
				
				Heraut.say(p, mH.getMsg(msgType.ERROR, "noPermissions", d));
			}
		}else{
			
		}
	}
}
