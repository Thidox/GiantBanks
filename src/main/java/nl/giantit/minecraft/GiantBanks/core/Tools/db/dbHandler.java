package nl.giantit.minecraft.GiantBanks.core.Tools.db;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Tools.db.Handler.*;

import java.util.HashMap;

public class dbHandler {

	private GiantBanks plugin;
	private HashMap<String, IHandler> handlers = new HashMap<String, IHandler>(); 
	
	public dbHandler(GiantBanks plugin) {
		this.plugin = plugin;
		handlers.put("Accounts", new Accounts());
		handlers.put("Types", new Types());
	}
	
	public void init() {
		for(IHandler handler : handlers.values()) {
			handler.init();
		}
	}
	
	public void init(dbType type) {
		if(type == null)
			type = dbType.ALL;
		
		switch(type) {
			case ACCOUNTS:
				handlers.get("Accounts").init();
				break;
			case TYPES:
				handlers.get("Types").init();
				break;
			case BANKS:
				//init banks
				break;
			case LOGS:
				//init logs
				break;
			default:
				handlers.get("Types").init();
				handlers.get("Accounts").init();
				break;
		}
	}
	
	public void save(dbType type) {
		if(type == null)
			type = dbType.ALL;
		
		switch(type) {
			case ACCOUNTS:
				handlers.get("Accounts").save();
				break;
			case TYPES:
				handlers.get("Types").save();
				break;
			case BANKS:
				//init banks
				break;
			case LOGS:
				//init logs
				break;
			default:
				handlers.get("Accounts").save();
				handlers.get("Types").save();
				break;
		}
	}
	
	public void fullSave(dbType type) {
		if(type == null)
			type = dbType.ALL;
		
		switch(type) {
			case ACCOUNTS:
				handlers.get("Accounts").fullSave();
				break;
			case TYPES:
				handlers.get("Types").fullSave();
				break;
			case BANKS:
				//init banks
				break;
			case LOGS:
				//init logs
				break;
			default:
				handlers.get("Accounts").fullSave();
				handlers.get("Types").fullSave();
				break;
		}
	}
	
	public IHandler getHandler(dbType type) {
		if(type == null)
			return null;
		
		switch(type) {
			case ACCOUNTS:
				return handlers.get("Accounts");
			case TYPES:
				return handlers.get("Types");
			case BANKS:
				//init banks
				break;
			case LOGS:
				//init logs
				break;
			default:
				return null;
		}
		
		return null;
	}
}
