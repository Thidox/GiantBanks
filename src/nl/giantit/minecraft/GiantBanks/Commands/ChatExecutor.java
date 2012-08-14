package nl.giantit.minecraft.GiantBanks.Commands;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Commands.Chat.*;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
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
		
		if(args.length == 0) {
			Have.exec(player, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "help", "hel", "he", "h", "?", "-h")) {
			Help.showHelp(player, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "store", "stor", "sto", "st", "s", "-s")) {
			Store.exec(player, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "get", "ge", "g", "-g")) {
			Get.exec(player, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "getall", "ga", "-ga")) {
			GetAll.exec(player, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "type", "typ", "ty", "t", "-t")) {
			Type.exec(player, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "account", "accoun", "accou", "acco", "acc", "ac", "a", "-acc", "-a")) {
			Account.exec(player, args);
		}else if(args[0].equalsIgnoreCase("have")) {
			Have.exec(player, args);
		}else{
			Heraut.say(player, "Ok, we have no friggin clue what you are on about, so what about we just send you our help page?");
			Help.showHelp(player, args);
		}
		
		return true;
	}
	
}