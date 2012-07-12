package nl.giantit.minecraft.GiantBanks.core.Tools.db.Handler;

import nl.giantit.minecraft.GiantBanks.Bank.AccountType;

public class Types implements IHandler {

	public Types() {}
		
	@Override
	public void init() {
		AccountType.createAccountType(0, "default", "");
	}

	@Override
	public void save() {
		
	}

	@Override
	public void fullSave() {
		
	}
}
