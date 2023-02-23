package stm;

import java.util.*;

public class ReadSet implements Iterable <Map.Entry<FieldInfo, Long>>{
	
	public Map<FieldInfo, Long> log = new HashMap<FieldInfo, Long>();

	public Iterator<Map.Entry<FieldInfo, Long>> iterator() {
		return log.entrySet().iterator();
	}
	
	public Long get(FieldInfo x) {
		return log.get(x);
	}
	
	public boolean put(FieldInfo x, Long i) {

		Long version = this.get(x);
		if (version!=null && version<i) {
			return false;
		}
		log.put(x, i);
		return true;
	}

	public String toString(){
		
		return ("ReadSet "+log.toString());
	}
    
}
