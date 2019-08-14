package org.thshsh.util.concurrent;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureUtils {
	
	public static <T> CompletableFuture<Void> allOf(Collection<CompletableFuture<T>> all){
		CompletableFuture<Void> allDone = CompletableFuture.allOf(all.toArray(new CompletableFuture[all.size()]));
		return allDone;
	}
	
	

}
