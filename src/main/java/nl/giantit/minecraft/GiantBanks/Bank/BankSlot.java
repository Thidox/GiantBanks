package nl.giantit.minecraft.GiantBanks.Bank;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.Items.ItemID;

public class BankSlot {

	private Integer slotID;
	
	private String item;
	private ItemID iID;
	private Integer amount;
	
	public BankSlot(String s) {
		String[] data = s.split("; ");
		for(int i = 0; i < data.length; i++) {
			String v = data[i];
			if(v.startsWith("BankSlot"))
				v = v.replace("BankSlot {", "");

			if(v.endsWith("}"))
				v = v.replace("}", "");
			
			v = v.replaceFirst("[a-zA-Z]+=", "");
			
			//Has to be better way for this...
			if(i == 0) {
				this.slotID = Integer.parseInt(v);
			}else if(i == 1) {
				this.item = v;
			}else if(i == 2) {
				this.iID = new ItemID(v);
			}else if(i == 3) {
				this.amount = Integer.parseInt(v);
			}
		}
	}
	
	public BankSlot(Integer slotID, String item, Integer amount) {
		this(slotID, item, amount, null);
	}

	public BankSlot(Integer slotID, String item, Integer amount, ItemID iID) {
		if(iID == null)
			iID = GiantBanks.getPlugin().getItemHandler().getItemIDByName(item);
		
		this.slotID = slotID;
		this.item = item;
		this.iID = iID;
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
	
	public Boolean contains(ItemID test) {
		return this.iID.equals(test);
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int setAmount(int amt) {
		this.amount = amt;
		
		return this.amount;
	}
	
	public int setAmount(int amt, Boolean incr) {
		if(incr)
			this.amount += amt;
		else
			this.amount -= amt;
		
		return this.amount;
	}

	@Override
	public String toString() {
		return "BankSlot {slotID=" + this.slotID + "; item=" + this.item + "; iID=" + this.iID.toString() + "; amount=" + this.amount + "}";
	}
}
