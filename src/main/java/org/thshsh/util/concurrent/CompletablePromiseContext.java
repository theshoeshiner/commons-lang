package org.thshsh.util.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletablePromiseContext<V> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CompletablePromiseContext.class);
	
    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();
    
   /* public static void schedule(Runnable r) {
        SERVICE.schedule(r, 500, TimeUnit.MILLISECONDS);
    }
    
    public static void schedule(Runnable r,long ms) {
        SERVICE.schedule(r, ms, TimeUnit.MILLISECONDS);
    }
    */
    protected long checkEvery = 1;
    protected List<CompletablePromise<V>> promises;
    
    public CompletablePromiseContext(long ms) {
    	promises = Collections.synchronizedList(new ArrayList<>());
    	this.checkEvery = ms;
    	//schedule();
    }
    
    public void add(CompletablePromise<V> promise) {
    	promises.add(promise);
    }
    
    /*public CompletablePromiseContext(List<CompletablePromise<V>> p) {
    	promises = Collections.synchronizedList(p);
    	schedule();
    }*/
    
    public void start() {
    	schedule();
    }
    
    protected void schedule() {
    	SERVICE.schedule(this::tryToComplete, checkEvery, TimeUnit.MILLISECONDS);
    }
    
    private void tryToComplete() {
    	LOGGER.debug("tryToComplete");
    	synchronized (promises) {
	    	for(Iterator<CompletablePromise<V>> it = promises.iterator();it.hasNext();) {
	    		CompletablePromise<V> prom = it.next();
	    		boolean remove = prom.tryToComplete();
	    		if(remove) it.remove();
	    	}
	    	
    	}
    	LOGGER.debug("{} Promises Left",promises.size());
    	if(!promises.isEmpty()) schedule();
    }
}