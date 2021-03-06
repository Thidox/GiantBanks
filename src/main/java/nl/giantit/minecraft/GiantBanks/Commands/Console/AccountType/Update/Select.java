package nl.giantit.minecraft.GiantBanks.Commands.Console.AccountType.Update;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class Select {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();

	public static void exec(CommandSender p, String[] args) {
		if(args.length > 3) {
			AccountType aT;
			try{
				aT = AccountType.getType(Integer.parseInt(args[3]));
			}catch(NumberFormatException e) {
				aT = AccountType.getType(args[3]);
			}

			if(null != aT) {
				if(sH.contains(p.getName() + ".selectedType"))
					sH.remove(p.getName() + ".selectedType");

				if(sH.store(p.getName() + ".selectedType", aT)) {
					HashMap<String, String> d = new HashMap<String, String>();
					d.put("id", String.valueOf(aT.getTypeID()));
					d.put("type", aT.getName());

					Heraut.say(p, mH.getConsoleMsg(msgType.ADMIN, "typeSelected", d));
				}else{
					Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "unknown"));
				}
			}else{
				HashMap<String, String> d = new HashMap<String, String>();
				d.put("type", args[3]);

				Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "typeNotFound", d));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "type update select");

			Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "syntaxError", d));
		}
	}

}
