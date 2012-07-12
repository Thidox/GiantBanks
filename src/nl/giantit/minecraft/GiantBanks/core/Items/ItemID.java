package nl.giantit.minecraft.GiantBanks.core.Items;

import java.util.HashMap;
import java.util.Map;

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
	
	public ItemID(String s) {
		String[] data = s.split(", ");
		for(int i = 0; i < data.length; i++) {
			String v = data[i];
			if(v.startsWith("ItemID"))
				v = v.replace("ItemID {", "");

			if(v.endsWith("}"))
				v = v.replace("}", "");
			
			v = v.replaceFirst("[a-zA-Z]+=", "");
			
			//Has to be better way for this...
			if(i == 0) {
				this.id = Integer.parseInt(v);
			}else if(i == 1) {
				this.type = Integer.parseInt(v);
			}else if(i == 2) {
				this.dura = Short.parseShort(v);
			}else if(i == 3) {
				this.name = v;
			}else if(i == 4) {
				this.hasType = Boolean.parseBoolean(v);
			}
		}
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
	
	public ItemID(Map<String, Object> map) {
		
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
	
	public boolean equals(ItemID key) {
		Integer type = (key.getType() == null) ? 0 : key.getType();
		return (key.getId() == this.id && type == this.type && key.getDurability() == this.dura);
	}
	
	@Override
	public String toString() {
		return "ItemID {id=" + this.id + ", type=" + this.type + ", dura=" + this.dura + ", name=" + this.name + ", hasType=" + this.hasType + "}";
	}
}
