package nl.giantit.minecraft.GiantBanks.core.Items;

/**
 *
 * @author Giant
 */
public class ItemID {
	
	private int id, type;
	private short dura = 0;
	private String name;
	private boolean hasType = false;
	
	public ItemID() {
		this(0, null);
	}
	
	public ItemID(int id) {
		this(id, null);
	}
	
	public ItemID(int id, Integer type) {
		this(id, type, null);
	}
	
	public ItemID(int id, Integer type, String name) {
		this.id = id;
		if(type == null) {
			this.type = 0;
			this.hasType = false;
		}else{
			this.type = type;
			this.hasType = true;
		}
		
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public Integer getType() {
		if(hasType) return type;
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public short setDurability(short dura) {
		this.dura = dura;
		return dura;
	}
	
	public short getDurability() {
		return this.dura;
	}
	
	@Override
	public String toString() {
		return "Item ID: " + this.id + " Item type: " + this.type;
	}
	
	public boolean equals(ItemID key) {
		Integer type = (key.getType() == null) ? 0 : key.getType();
		return (key.getId() == this.id && type == this.type && key.getDurability() == this.dura);
	}
}
