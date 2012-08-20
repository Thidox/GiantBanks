package nl.giantit.minecraft.GiantBanks.Commands;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.Commands.Console.Help;
import nl.giantit.minecraft.GiantBanks.Commands.Console.Type;
import nl.giantit.minecraft.GiantBanks.core.Misc.Heraut;
import nl.giantit.minecraft.GiantBanks.core.Misc.Misc;

import org.bukkit.command.CommandSender;

public class ConsoleExecutor {

	private GiantBanks plugin;
	
	public ConsoleExecutor(GiantBanks plugin) {
		this.plugin = plugin;
	}
	
	public boolean exec(CommandSender sender , String[] args) {
		if(args.length == 0 || Misc.isAnyIgnoreCase(args[0], "help", "hel", "he", "h", "?", "-h")) {
			Help.showHelp(sender, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "sendhelp", "sh", "-sh")) {
			Help.sendHelp(sender, args);
		}else if(Misc.isAnyIgnoreCase(args[0], "type", "typ", "ty", "t", "-t")) {
			Type.exec(sender, args);
		}else{
			//GiantBanks.getPlugin().getDB().getEngine().Truncate("#__accounts").updateQuery();
			//sender.sendMessage("fuck off");
			//Heraut.say(sender, "Truncating items table!");
			Heraut.say(sender, "Ok, we have no friggin clue what you are on about, so what about we just send you our help page?");
			Help.showHelp(sender, args);
		}
		
		return true;
	}
}
