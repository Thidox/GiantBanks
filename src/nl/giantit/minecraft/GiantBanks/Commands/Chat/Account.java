package nl.giantit.minecraft.GiantBanks.Commands.Chat;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Commands.Chat.UserAccount.*;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;

import org.bukkit.entity.Player;

public class Account {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();
	
	public static void exec(Player p, String[] args) {
		if(args.length >= 2) {
			if(Misc.isAnyIgnoreCase(args[1], "have", "hav", "ha", "h", "-h", "")) {
				Have.exec(p, args);
			}else if(Misc.isAnyIgnoreCase(args[1], "select", "selec", "sele", "sel", "se", "s", "-s")) {
				Select.exec(p, args);
			}else if(Misc.isAnyIgnoreCase(args[1], "has", "-has")) {
				Has.exec(p, args);
			}else if(Misc.isAnyIgnoreCase(args[1], "clear", "clea", "cle", "cl", "c", "-c")) {
				Clear.exec(p, args);
			}else if(Misc.isAnyIgnoreCase(args[1], "type", "typ", "ty", "t", "-t", "settype", "st", "-st")) {
				SetType.exec(p, args);
			}else if(Misc.isAnyIgnoreCase(args[1], "remove", "remov", "remo", "rem", "re", "r", "-rem", "-rm", "-r")) {
				Remove.exec(p, args);
			} 
		}else{
			Have.exec(p, args);
		}
	}
}
