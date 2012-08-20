package nl.giantit.minecraft.GiantBanks.core.Logger;

public enum LoggerType {
	ATC(1, "AccountType creation"),
	ATM(2, "AccountType modification"),
	ATR(3, "AccountType removal"),
	UAC(4, "UserAccount creation"),
	UAM(5, "UserAccount modification"),
	UAR(6, "UserAccount removal"),
	UAIS(7, "UserAccount item storing"),
	UAIT(8, "UserAccount item taking"),
	UNKNOWN(20, "unknown");
	
	private int id;
	private String name;
	
	private LoggerType(Integer i, String n) {
		id = i;
		name = n;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
