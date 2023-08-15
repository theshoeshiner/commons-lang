package org.thshsh.text;

import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;


@SuppressWarnings("serial")
@Deprecated
public class SafeSimpleDateFormat extends SimpleDateFormat {
	
    private final String formatString;
    private final TimeZone timeZone;
    
    private static final ThreadLocal<Map<String, SimpleDateFormat>> FORMAT_MAP = new ThreadLocal<Map<String, SimpleDateFormat>>()
    {
        public Map<String, SimpleDateFormat> initialValue() {
            return new ConcurrentHashMap<String, SimpleDateFormat>();
        }
    };

    public static SimpleDateFormat getDateFormat(String format,TimeZone tz) {
        Map<String, SimpleDateFormat> formatters = FORMAT_MAP.get();
        if(!formatters.containsKey(format)){
        	SimpleDateFormat sdf = new SimpleDateFormat(format);
        	sdf.setTimeZone(tz);
        	formatters.put(format, sdf);
        }
        return formatters.get(format);
    }

    public SafeSimpleDateFormat(String format){
       this(format,TimeZone.getDefault());
    }
    
    public SafeSimpleDateFormat(String format,TimeZone tz){
    	super(format);
        formatString = format;
        timeZone = tz;
    }
    
    public String getFormatString(){
    	return formatString;
    }
    
    public SimpleDateFormat getDateFormat(){
    	return getDateFormat(formatString, timeZone);
    }

	/*
	 * These methods are from SimpleDateFormat directly 
	 */
    
    public void setDateFormatSymbols(DateFormatSymbols symbols){
        getDateFormat().setDateFormatSymbols(symbols);
    }

    public void set2DigitYearStart(Date date){
        getDateFormat().set2DigitYearStart(date);
    }
    
    /*
     * Abstract methods
     */

    @Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		  return getDateFormat().format(date, toAppendTo, fieldPosition);
	}

    @Override
	public Date parse(String source, ParsePosition pos) {
		return getDateFormat().parse(source, pos);
	}
    
    /*
	 * These methods are from DateFormat and we're overriding them so you cant really access the DateFormat logic of this class 
	 */
    
    @Override
    public void setTimeZone(TimeZone tz) {
        getDateFormat().setTimeZone(tz);
    }
    
    @Override
    public void setCalendar(Calendar cal){
        getDateFormat().setCalendar(cal);
    }
    
    @Override
    public void setNumberFormat(NumberFormat format){
        getDateFormat().setNumberFormat(format);
    }

    @Override
    public void setLenient(boolean lenient){
        getDateFormat().setLenient(lenient);
    }
    
    @Override
	public Object clone() {
		return new SafeSimpleDateFormat(formatString,timeZone);
	}

	@Override
	public Calendar getCalendar() {
		return getDateFormat().getCalendar();
	}

	@Override
	public NumberFormat getNumberFormat() {
		return getDateFormat().getNumberFormat();
	}

	@Override
	public TimeZone getTimeZone() {
		return getDateFormat().getTimeZone();
	}

	@Override
	public boolean isLenient() {
		return getDateFormat().isLenient();
	}

}