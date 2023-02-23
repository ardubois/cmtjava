package stm;

import java.util.concurrent.atomic.AtomicLong;

public class VersionClock {
	
	static AtomicLong global = new AtomicLong(0L);

	public static long getWriteStamp() {
		return global.incrementAndGet();
	}

	public static long getReadStamp() {
		return global.get();
	}
}
