package org.thshsh.util;

import java.util.function.Function;

public class FunctionUtils {
	
	public static <A,B,Z> Function<A,Z> nested(Function<A,B> fstart,Function<B, Z> fend) {
		return nestedDefault(fstart, fend, (Z)null);
	}
	
	public static <A,B,C,Z> Function<A,Z> nested(Function<A,B> fstart,Function<B, C> fmiddle,Function<C, Z> fend) {
		Function<A,C> f0 = nestedDefault(fstart, fmiddle, (C)null);
		return nestedDefault(f0, fend, (Z)null);
	}
	
	public static <A,B,Z> Function<A,Z> nestedDefault(Function<A, B> fstart, Function<B,Z> fend, Z nullValue) {
		return (t) -> {
			B r = fstart.apply(t);
			if(r == null) return nullValue;
			return fend.apply(r);
		};
	}

}
