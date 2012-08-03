package nl.giantit.minecraft.GiantBanks.core.Updater;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Updater.Database.dbUpdate;

public class Updater {

	private GiantBanks plugin;
	
	public Updater(GiantBanks plugin) {
		this.plugin = plugin;
	}
	
	public void Update(UpdateType t) {
		switch(t) {
			case CONFIG:
				//Config.Update();
				break;
			case DATABASE:
				dbUpdate.Update();
				break;
			case ITEMS:
				//Items.Update();
				break;
			case LANGFILE:
				//Config.Update();
				break;
			case MESSAGETPL:
				//MessageTPL.Update();
				break;
			default:
				break;
		}
	}
	
}
