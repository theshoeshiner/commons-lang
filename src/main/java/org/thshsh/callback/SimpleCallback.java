package org.thshsh.callback;

import lombok.Getter;

@Getter
public abstract class SimpleCallback<STATUS extends SimpleStatus,STATE extends SimpleState> implements StatusCallback<STATUS,STATE> {

	protected STATE state;

	@Override
	public void state(STATE state) {
		this.state = state;
	}
	
	public boolean isCancelled() {
		 return (state != null) ? state.isCancelled() : false;
	}

}
