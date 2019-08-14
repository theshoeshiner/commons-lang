package org.thshsh;

import java.util.concurrent.Callable;

public class RunnableCallable implements Callable<Void> {

	protected Runnable runnable;
	
	public RunnableCallable(Runnable run) {
		this.runnable = run;
	}
	
	@Override
	public Void call() throws Exception {
		runnable.run();
		return null;
	}

}
