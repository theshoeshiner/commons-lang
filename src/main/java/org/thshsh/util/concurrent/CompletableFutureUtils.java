package org.thshsh.util.concurrent;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CompletableFutureUtils {
	
	public static <T> CompletableFuture<Void> allOf(Collection<CompletableFuture<T>> all){
		CompletableFuture<Void> allDone = CompletableFuture.allOf(all.toArray(new CompletableFuture[all.size()]));
		return allDone;
	}
	

	
	public static <T> CompletableFuture<Void> allOfFuture(Collection<Future<T>> all,long ms){
		
		CompletablePromiseContext<T> context = new CompletablePromiseContext<T>(ms);
		List<CompletableFuture<T>> futures = new LinkedList<>();
		for(Future<T> f : all) {
			futures.add(new CompletablePromise<>(f,context));
		}
		context.start();
		CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		return allDone;
	}

}
