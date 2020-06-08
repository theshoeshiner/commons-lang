package org.thshsh;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RandomUtils {
	
	public static <T> T nextItem(T[] coll) {
		return nextItem(Arrays.asList(coll));
	}
	
	public static <T> T nextItem(Collection<T> coll) {
		if(coll.size()==0)return null;
		else if(coll.size()==1)return coll.iterator().next();
		else {
			int index = org.apache.commons.lang3.RandomUtils.nextInt(0, coll.size());
			if(coll instanceof List) {
				return ((List<T>) coll).get(index);
			}
			else {
				return IteratorUtils.get(coll.iterator(), index);
			}
		}
		
	}

}
