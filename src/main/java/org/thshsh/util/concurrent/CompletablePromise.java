package org.thshsh.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletablePromise<V> extends CompletableFuture<V> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CompletablePromise.class);
	
    private Future<V> future;

    public CompletablePromise(Future<V> future,CompletablePromiseContext<V> context) {
        this.future = future;
        context.add(this);
        //CompletablePromiseContext.schedule(this::tryToComplete,ms);
    }
    
    public CompletablePromise(Future<V> future) {
        this.future = future;
        CompletablePromiseContext<V> context = new CompletablePromiseContext<>(1000);
        context.add(this);
        context.start();
        //CompletablePromiseContext.schedule(this::tryToComplete);
    }


    protected boolean tryToComplete() {
    	//LOGGER.info("tryToComplete");
    	
        if (future.isDone()) {
            try {
                complete(future.get());
            } catch (InterruptedException e) {
                completeExceptionally(e);
            } catch (ExecutionException e) {
                completeExceptionally(e.getCause());
            }
            return true;
        }
        else if (future.isCancelled()) {
            cancel(true);
            return true;
        }
        else return false;

        //CompletablePromiseContext.schedule(this::tryToComplete);
    }
}