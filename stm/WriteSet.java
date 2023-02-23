package stm;

import java.util.*;

public class WriteSet implements Iterable <Map.Entry<FieldInfo, Object>> {
	
	public Map<FieldInfo, Object> log = new HashMap<FieldInfo, Object>();

	public Iterator<Map.Entry<FieldInfo, Object>> iterator() {
		return log.entrySet().iterator();
	}
	
	public Object get(FieldInfo x) {
		return log.get(x);
	}
	
	public void put(FieldInfo x, Object o) {
		log.put(x, o);
	}
	
	public String toString(){
		return ("WriteSet "+log.toString());
	}
	
}
