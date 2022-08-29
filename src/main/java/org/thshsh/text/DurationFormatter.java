package org.thshsh.text;

import java.time.Duration;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class DurationFormatter {
	
	String hoursFormat;
	String minutesFormat;
	String secondsFormat;
	String zeroFormat;
	
	public DurationFormatter(String totalFormat) {
		this(null, null, totalFormat,null);
	}
	
	public DurationFormatter(String hours, String minutes, String seconds, String zero) {
		this.hoursFormat = hours;
		this.minutesFormat = minutes;
		this.secondsFormat = seconds;
		this.zeroFormat = zero;
	}
	
	public String format(Duration d) {
		long dms = d.toMillis();
		if(dms == 0 && zeroFormat != null) return DurationFormatUtils.formatDuration(d.toMillis(), zeroFormat);
		else {
			StringBuilder format = new StringBuilder();
			if(dms > 3_600_000 && hoursFormat != null) format.append(hoursFormat);
			if(dms > 60_000 && minutesFormat != null) format.append(minutesFormat);
			if(secondsFormat != null) format.append(secondsFormat);
			return DurationFormatUtils.formatDuration(dms, format.toString());
		}
		
	}

}
