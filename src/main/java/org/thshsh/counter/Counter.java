package org.thshsh.counter;


public class Counter {
	
	protected Long start = 0l;
	protected Long maximum = Long.MAX_VALUE;
	protected Long step = 1l;
	protected Long value = 0l;
	
	public Counter(Long start, Long max, Long step){
		this.start = start;
		this.maximum = max;
		this.step = step;
		this.value = start;
	}
	
	public void step(){
		value = value+=step;
		if(maximum!=null && value>maximum)value = maximum;
	}
	
	public void reset(){
		this.value = start;
	}

	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}

	public Long getMaximum() {
		return maximum;
	}
	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}

	public Long getStep() {
		return step;
	}
	public void setStep(Long step) {
		this.step = step;
	}

	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
	

}
