package nl.giantit.minecraft.GiantBanks.Commands.Console.AccountType;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Items.Items;
import nl.giantit.minecraft.GiantBanks.core.Misc.Messages;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Create {

	private static Items iH = GiantBanks.getPlugin().getItemHandler();
	private static Messages mH = GiantBanks.getPlugin().getMsgHandler();

	public static void exec(CommandSender s, String[] args) {
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

		}
	}

}
