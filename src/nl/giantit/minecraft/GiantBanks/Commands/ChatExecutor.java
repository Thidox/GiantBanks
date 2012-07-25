package nl.giantit.minecraft.GiantBanks.Commands;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Commands.Chat.*;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ChatExecutor {

	private GiantBanks plugin;
	
	public ChatExecutor(GiantBanks plugin) {
		this.plugin = plugin;
	}
	
	public boolean exec(CommandSender sender , String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0 || Misc.isAnyIgnoreCase(args[0], "help", "hel", "he", "h", "?", "-h")) {
			Help.showHelp(player, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "store", "stor", "sto", "st", "s", "-s")) {
			Store.exec(player, args);
			/*Heraut.say(player, "Ok, we have no friggin clue what you are on about, so what about we just send you our help page?");
			help.showHelp(player, args);*/
		}else if(Misc.isAnyIgnoreCase(args[0], "get", "ge", "g", "-g")) {
			Get.exec(player, args);
			/*Heraut.say(player, "Ok, we have no friggin clue what you are on about, so what about we just send you our help page?");
			help.showHelp(player, args);*/
		}else{
			//Heraut.say(player, "Ok, we have no friggin clue what you are on about, so what about we just send you our help page?");
			Help.showHelp(player, args);
		}
		
		return true;
	}
	
}