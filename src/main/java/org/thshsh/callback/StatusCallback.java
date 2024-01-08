package org.thshsh.callback;

public interface StatusCallback<STATUS extends Status, STATE extends State> {

	public void status(STATUS status);
	
	public void state(STATE state);
	
	public STATE getState();
	
}
