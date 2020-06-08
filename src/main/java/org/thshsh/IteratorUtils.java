package org.thshsh;

import java.util.Iterator;

public class IteratorUtils {
	
	public static <T> T get(Iterator<T> it, int count) {
		for(int i=0;i<count;i++) {
			it.next();
		}
		return it.next();
	}

}
