package org.thshsh.callback;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ProgressStatus extends SimpleStatus {
	
	protected int total;
	protected int current;

	public ProgressStatus(int total, int current) {
		super();
		this.total = total;
		this.current = current;
	}
	
	public ProgressStatus() {
		super();
	}
	
}
