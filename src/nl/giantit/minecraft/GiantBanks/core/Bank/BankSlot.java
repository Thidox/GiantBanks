package nl.giantit.minecraft.GiantBanks.core.Bank;

public class BankSlot {

	private Integer slotID;
	
	private String item;
	private Integer amount;
	
	public BankSlot(Integer slotID, String item, Integer amount) {
		this.slotID = slotID;
		this.item = item;
		this.amount = amount;
	}
	
	public int getSlotID() {
		return this.slotID;
	}
	
	public String getItem() {
		return this.item;
	}
	
	public String setItem(String item) {
		this.item = item;
		return this.item;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int setAmount(int amt, Boolean incr) {
		if(incr)
			this.amount += amt;
		else
			this.amount -= amt;
		
		return this.amount;
	}
}
