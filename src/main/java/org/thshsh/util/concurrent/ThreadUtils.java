package org.thshsh.util.concurrent;

public class ThreadUtils {

	public static void sleepSafe(long time){
		try {
			Thread.sleep(time);
		}
		catch (InterruptedException e) {}
	}
	
}
