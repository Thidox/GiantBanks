package nl.giantit.minecraft.GiantBanks.Commands.Chat;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Commands.Chat.AccountType.Storable.Add;
import nl.giantit.minecraft.GiantBanks.Commands.Chat.AccountType.Update.Select;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;

import org.bukkit.entity.Player;

public class Type {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	
	public static void exec(Player p, String[] args) {
		if(args.length >= 3) {
			if(Misc.isAnyIgnoreCase(args[1], "update", "updat", "upda", "upd", "up", "u", "-u")) {
				if(Misc.isAnyIgnoreCase(args[2], "select", "selec", "sele", "sel", "se", "s", "-s")) {
					Select.exec(p, args);
				}else if(Misc.isAnyIgnoreCase(args[2], "set", "-set")) {
					//Set.exec(p, args);
				}else if(Misc.isAnyIgnoreCase(args[2], "save", "sav", "sa", "-save", "-sav", "-sa")) {
					//Save.exec(p, args);
				}
			}else if(Misc.isAnyIgnoreCase(args[1], "storable", "storabl", "storab", "stora", "stor", "sto", "st", "s", "-s")) {
				if(Misc.isAnyIgnoreCase(args[2], "add", "ad", "a", "-a")) {
					Add.exec(p, args);
				}else if(Misc.isAnyIgnoreCase(args[2], "remove", "remov", "remo", "rem", "re", "r", "-rem", "-r")) {
					//Remove.exec(p, args);
				}
			}
		}
	}
}
