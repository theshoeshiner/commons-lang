package org.thshsh.util;

import java.util.function.Function;

public class FunctionUtils {
	
	public static <T,R,C> Function<T,C> nested(Function<T, R> func,Function<R, C> sub) {
		return (t) -> {
			R r = func.apply(t);
			return sub.apply(r);
		};
	}

}
