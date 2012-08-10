package nl.giantit.minecraft.GiantBanks.Commands.Console.AccountType.Update;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;
import nl.giantit.minecraft.GiantBanks.core.Tools.Register;
import nl.giantit.minecraft.GiantBanks.core.perms.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Set {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	private static Register sH = Register.getInstance();

	public static void exec(CommandSender p, String[] args) {
		if(args.length >= 5) {
			if(sH.contains(p.getName() + ".selectedType")) {
				AccountType aT = (AccountType) sH.get(p.getName() + ".selectedType");
				if(Misc.isAnyIgnoreCase(args[3], "name", "nam", "na", "n", "-n")) {
					if(args[4] != null && !args[4].equals("")) {
						aT.setName(args[4]);

						HashMap<String, String> d = new HashMap<String, String>();
						d.put("id", String.valueOf(aT.getTypeID()));
						d.put("type", aT.getName());

						Heraut.say(p, mH.getConsoleMsg(msgType.ADMIN, "typeNameChanged", d));
					}else{
						HashMap<String, String> d = new HashMap<String, String>();
						d.put("command", "type update set name");

						Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "syntaxError", d));
					}
				}else if(Misc.isAnyIgnoreCase(args[3], "maxslots", "maxs", "m", "ms", "-m", "-ms")) {
					try{
						aT.setMaxSlots(Integer.parseInt(args[4]));

						HashMap<String, String> d = new HashMap<String, String>();
						d.put("id", String.valueOf(aT.getTypeID()));
						d.put("type", aT.getName());
						d.put("maxSlots", String.valueOf(aT.getMaxSlots()));

						Heraut.say(p, mH.getConsoleMsg(msgType.ADMIN, "typeMSChanged", d));
					}catch(NumberFormatException e) {
						HashMap<String, String> d = new HashMap<String, String>();
						d.put("command", "type update set maxslots");

						Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "syntaxError", d));
					}
				}else if(Misc.isAnyIgnoreCase(args[3], "maxperslot", "maxps", "mps", "mp", "-mps", "-mp")) {
					try{
						aT.setMaxPerSlots(Integer.parseInt(args[4]));

						HashMap<String, String> d = new HashMap<String, String>();
						d.put("id", String.valueOf(aT.getTypeID()));
						d.put("type", aT.getName());
						d.put("maxPerSlot", String.valueOf(aT.getMaxPerSlot()));

						Heraut.say(p, mH.getConsoleMsg(msgType.ADMIN, "typeMPSChanged", d));
					}catch(NumberFormatException e) {
						HashMap<String, String> d = new HashMap<String, String>();
						d.put("command", "type update set maxperslot");

						Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "syntaxError", d));
					}
				}
			}else{
				Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "noTypeSelected"));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "type update set");

			Heraut.say(p, mH.getConsoleMsg(msgType.ERROR, "syntaxError", d));
		}
	}
}
