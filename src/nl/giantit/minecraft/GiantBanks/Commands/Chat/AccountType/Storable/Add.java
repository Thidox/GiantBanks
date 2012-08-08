package nl.giantit.minecraft.GiantBanks.Commands.Chat.AccountType.Storable;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Add {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();
	
	public static void exec(Player p, String[] args) {
		if(args.length > 3) {
			if(sH.contains(p.getName() + ".selectedType")) {
				AccountType aT = (AccountType) sH.get(p.getName() + ".selectedType");
				//aT.setStorable(true, "");
				
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
