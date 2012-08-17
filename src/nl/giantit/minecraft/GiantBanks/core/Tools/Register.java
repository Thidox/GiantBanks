package nl.giantit.minecraft.GiantBanks.core.Tools;

import java.util.concurrent.ConcurrentHashMap;

public class Register {

	private static Register _Instance;
	
	private ConcurrentHashMap<String, Object> stored;
	
	private Register() {
		this.stored = new ConcurrentHashMap<String, Object>();
	}
	
	public Boolean contains(String K) {
		return this.stored.containsKey(K);
	}
	
	public Boolean store(String K, Object V) {
		if(this.stored.containsKey(K))
			return false;
		
		this.stored.put(K, V);
		return true;
	}
	
	public Boolean store(String K, Object V, Boolean override) {
		if(this.stored.containsKey(K) && !override)
			return false;
		
		this.stored.put(K, V);
		return true;
	}
	
	public Object get(String K) {
		if(!this.stored.containsKey(K))
			return null;
		
		return this.stored.get(K);
	}
	
	public Object remove(String K) {
		if(!this.stored.containsKey(K))
			return null;
		
		return this.stored.remove(K);
	}
	
	public static Register getInstance() {
		if(null == _Instance || !(_Instance instanceof Register)) {
			_Instance = new Register();
		}
		
		return _Instance;
	}
}
