package org.thshsh.util;

import java.util.function.Function;

public class FunctionUtils {
	
	public static <A,B,Z> Function<A,Z> nested(Function<A,B> fstart,Function<B, Z> fend) {
		return (t) -> {
			B r = fstart.apply(t);
			return fend.apply(r);
		};
	}
	
	public static <A,B,C,Z> Function<A,Z> nested(Function<A,B> fstart,Function<B, C> f1,Function<C, Z> fend) {
		return (t) -> {
			B b = fstart.apply(t);
			C c = f1.apply(b);
			return fend.apply(c);
		};
	}

}
