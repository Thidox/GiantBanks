package nl.giantit.minecraft.GiantBanks.Commands.Console;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Commands.Console.AccountType.*;
import nl.giantit.minecraft.GiantBanks.Commands.Console.AccountType.Update.*;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;

import org.bukkit.command.CommandSender;

public class Type {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();

	public static void exec(CommandSender p, String[] args) {
		if(args.length >= 3) {
			if(Misc.isAnyIgnoreCase(args[1], "create", "creat", "crea", "cre", "cr", "c", "-c")) {
				//Create.exec(p, args);
			}else if(Misc.isAnyIgnoreCase(args[1], "update", "updat", "upda", "upd", "up", "u", "-u")) {
				if(Misc.isAnyIgnoreCase(args[2], "select", "selec", "sele", "sel", "se", "s", "-s")) {
					Select.exec(p, args);
				}else if(Misc.isAnyIgnoreCase(args[2], "set", "-set")) {
					Set.exec(p, args);
				}
			}else if(Misc.isAnyIgnoreCase(args[1], "storable", "storabl", "storab", "stora", "stor", "sto", "st", "s", "-s")) {
				Storable.exec(p, args);
			}else if(Misc.isAnyIgnoreCase(args[1], "remove", "remov", "remo", "rem", "re", "r", "-rem", "-rm", "-r")) {
				//Remove.exec(p, args);
			} 
		}
	}
}
