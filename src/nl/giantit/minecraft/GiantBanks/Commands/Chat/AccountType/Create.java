package nl.giantit.minecraft.GiantBanks.Commands.Chat.AccountType;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Bank.AccountType;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages.msgType;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Create {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	
	public static void exec(Player p, String[] args) {
		if(args.length > 3) {
			String name = null;
			int maxSlot = 20;
			int maxPerSlot = 20;
			
			for(int i = 1; i < args.length; i++) {
				if(args[i].equalsIgnoreCase("-n")) {
					i++;
					if(args.length - 1 >= i) {
						name = args[i];
						continue;
					}else{
						break;
					}
				}else if(args[i].startsWith("-n:")) {
					name = args[i].replaceFirst("-n:", "");
					continue;
				}else if(args[i].equalsIgnoreCase("-ms")) {
					i++;
					if(args.length - 1 >= i) {
						try{
							maxSlot = Integer.parseInt(args[i].replaceFirst("-ms", ""));
						}catch(NumberFormatException e) {
							//ignore
						}
						continue;
					}else{
						break;
					}
				}else if(args[i].startsWith("-ms:")) {
					try{
						maxSlot = Integer.parseInt(args[i].replaceFirst("-ms:", ""));
					}catch(NumberFormatException e) {
						//ignore
					}
					continue;
				}else if(args[i].equalsIgnoreCase("-mps")) {
					i++;
					if(args.length - 1 >= i) {
						try{
							maxPerSlot = Integer.parseInt(args[i].replaceFirst("-mps", ""));
						}catch(NumberFormatException e) {
							//ignore
						}
						continue;
					}else{
						break;
					}
				}else if(args[i].startsWith("-mps:")) {
					try{
						maxPerSlot = Integer.parseInt(args[i].replaceFirst("-mps:", ""));
					}catch(NumberFormatException e) {
						//ignore
					}
					continue;
				}
			}
			
			if(name != null) {
				if(null == AccountType.getType(name)) {
					AccountType aT = AccountType.createAccountType(name, maxSlot, maxPerSlot);
					HashMap<String, String> d = new HashMap<String, String>();
					d.put("id", String.valueOf(aT.getTypeID()));
					d.put("type", aT.getName());
					d.put("maxSlot", String.valueOf(aT.getMaxSlots()));
					d.put("maxPerSlot", String.valueOf(aT.getMaxPerSlot()));
					
					Heraut.say(p, mH.getMsg(msgType.ADMIN, "typeCreated", d));
				}else{
					//TypeNameCollission
					HashMap<String, String> d = new HashMap<String, String>();
					d.put("type", name);
					
					Heraut.say(p, mH.getMsg(msgType.ERROR, "typeNameCollision", d));
				}
			}else{
				Heraut.say(p, mH.getMsg(msgType.ERROR, "typeNameNotPassed"));
			}
		}else{
			HashMap<String, String> d = new HashMap<String, String>();
			d.put("command", "type create");
			
			Heraut.say(p, mH.getMsg(msgType.ERROR, "syntaxError", d));
		}
	}
	
}
