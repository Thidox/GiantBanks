package nl.giantit.minecraft.GiantBanks.Commands;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Commands.Chat.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ChatExecutor {

	private GiantBanks plugin;
	
	public ChatExecutor(GiantBanks plugin) {
		this.plugin = plugin;
	}
	
	public boolean exec(CommandSender sender , String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("?")) {
			Help.showHelp(player, args);
		}else{
			Store.Store(player, args);
			/*Heraut.say(player, "Ok, we have no friggin clue what you are on about, so what about we just send you our help page?");
			help.showHelp(player, args);*/
		}
		
		return true;
	}
	
}