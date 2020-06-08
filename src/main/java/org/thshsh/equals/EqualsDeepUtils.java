package org.thshsh.equals;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class EqualsDeepUtils {
	
	public static boolean equalsDeep(Collection<?> col1,Collection<?> col2) {
		boolean eq = col1.equals(col2);
		if(eq) {
			//TODO need to handle Sets here
			if(col1 instanceof List) {
				Iterator<?> it1 = col1.iterator();
				Iterator<?> it2 = col2.iterator();
				for(;it2.hasNext();) {
					Object ob1 = it1.next();
					Object ob2 = it2.next();
					eq = equalsDeep(ob1, ob2);
					if(!eq) break;
				}
			}
			else throw new IllegalStateException("Only lists supported currently");
		}
		return eq;
	}
	
	public static boolean equalsDeep(Object expected,Object check) {
		if(expected instanceof EqualsDeep) return equalsDeep((EqualsDeep)expected, check);
		else return Objects.equals(expected, check);
	}
	
	public static boolean equalsDeep(EqualsDeep expected,Object check) {
		return expected.equalsDeep(check);
	}
	


}
