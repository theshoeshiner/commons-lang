package org.thshsh;

public class StoppableThread extends Thread {
	
	protected boolean stopped = false;

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
		this.interrupt();
	}
	
	public static void sleepSafe(long ms) {
		try {Thread.sleep(ms);} 
		catch (InterruptedException e) {}
	}
	
}
