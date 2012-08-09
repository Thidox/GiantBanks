package nl.giantit.minecraft.GiantBanks.Commands.Chat.AccountType.Update;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;
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
		if(args.length >= 4) {
			if(pH.has(p, "giantbanks.admin.type.set")) {
				if(sH.contains(p.getName() + ".selectedType")) {
					AccountType aT = (AccountType) sH.get(p.getName() + ".selectedType");
					if(Misc.isAnyIgnoreCase(args[2], "name", "nam", "na", "n", "-n")) {
						if(args[3] != null && !args[3].equals("")) {
							aT.setName(args[3]);
							
							HashMap<String, String> d = new HashMap<String, String>();
							d.put("id", String.valueOf(aT.getTypeID()));
							d.put("type", aT.getName());
							
							Heraut.say(p, mH.getMsg(msgType.ADMIN, "typeNameChanged", d));
						}else{
							HashMap<String, String> d = new HashMap<String, String>();
							d.put("command", "type update set name");
							
							Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
						}
					}else if(Misc.isAnyIgnoreCase(args[2], "maxslots", "maxs", "m", "ms", "-m", "-ms")) {
						try{
							aT.setMaxSlots(Integer.parseInt(args[3]));
							
							HashMap<String, String> d = new HashMap<String, String>();
							d.put("id", String.valueOf(aT.getTypeID()));
							d.put("type", aT.getName());
							d.put("maxSlot", String.valueOf(aT.getMaxSlots()));
							
							Heraut.say(p, mH.getMsg(msgType.ADMIN, "typeMSChanged", d));
						}catch(NumberFormatException e) {
							HashMap<String, String> d = new HashMap<String, String>();
							d.put("command", "type update set maxslots");
							
							Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
						}
					}else if(Misc.isAnyIgnoreCase(args[2], "maxperslot", "maxps", "mps", "mp", "-mps", "-mp")) {
						try{
							aT.setMaxPerSlots(Integer.parseInt(args[3]));
							
							HashMap<String, String> d = new HashMap<String, String>();
							d.put("id", String.valueOf(aT.getTypeID()));
							d.put("type", aT.getName());
							d.put("maxPerSlot", String.valueOf(aT.getMaxPerSlot()));
							
							Heraut.say(p, mH.getMsg(msgType.ADMIN, "typeMPSChanged", d));
						}catch(NumberFormatException e) {
							HashMap<String, String> d = new HashMap<String, String>();
							d.put("command", "type update set maxperslot");
							
							Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
						}
					}
				}else{
					Heraut.say(p, mH.getMsg(msgType.ERROR, "noTypeSelected"));
				}
			}else{
				HashMap<String, String> d = new HashMap<String, String>();
				d.put("command", "type update set");
				
				Heraut.say(p, mH.getMsg(msgType.ERROR, "noPermissions", d));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "type update set");
			
			Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
		}
	}
}
