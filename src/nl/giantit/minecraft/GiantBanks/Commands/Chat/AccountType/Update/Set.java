package nl.giantit.minecraft.GiantBanks.Commands.Chat.AccountType.Update;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Set {

	private static Permission pH = GiantBanks.getPlugin().getPermHandler();
	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();

	public static void exec(Player p, String[] args) {
		if(args.length > 3) {
			if(pH.has(p, "giantbanks.admin.type.set")) {
				
			}else{
				HashMap<String, String> d = new HashMap<String, String>();
				d.put("command", "type set");
				
				Heraut.say(p, mH.getMsg(msgType.ERROR, "noPermissions", d));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "type set");
			
			Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
		}
	}
}
