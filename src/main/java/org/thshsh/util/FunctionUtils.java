package org.thshsh.util;

import java.util.function.Function;

public class FunctionUtils {
	
	public static <T,R> Function<T,?> nested(Function<T, R> func,Function<R, ?> sub) {
		return (t) -> {
			R r = func.apply(t);
			return sub.apply(r);
		};
	}

}
