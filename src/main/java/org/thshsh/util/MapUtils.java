package org.thshsh.util;

import java.util.HashMap;

/**
 * @deprecated use {@link org.apache.commons.collections4.MapUtils#putAll}
 *
 */
@Deprecated()
public class MapUtils {

	@SuppressWarnings("unchecked")
	public static <A, B> HashMap<A,B> createHashMap(A a,B b, Object... o){
		HashMap<A,B> map = new HashMap<>();
		map.put(a, b);
		for(int i=0;i<o.length;i++) {
			map.put((A)o[i++],(B)o[i]);
		}
		return map;
	}
	
}
