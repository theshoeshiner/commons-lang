package org.thshsh.text;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StyledStringUtils {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(StyledStringUtils.class);

	public static String REGEX_UC_CHAR = "(?=(\\p{Upper}))";
	//Splits on caps, except first, so it works for camel case and proper case, also combines strings of caps like abbreviations
	public static String REGEX_NOT_FIRST_UC_CHAR = "(?<=.)(?<!(\\p{Upper}))(?=(\\p{Upper}+))"; 
	public static String REGEX_SP_CHAR = "(\\s++)";
	public static String REGEX_US_CHAR = "[_]";
	public static String REGEX_DASH_CHAR = "[-]";
	
	public static String generateProperStyleString(String original){
		String[] parts = getParts(original);
		return generateStyledString(parts, " ", false, false, false,true);
	}
	
	public static String generateStaticStyleString(String original){
		String[] parts = getParts(original);
		return generateStyledString(parts, "_", true, false, false,false);
	}
	
	public static String generateVariableStyleString(String original){
		String[] parts = getParts(original);
		return generateStyledString(parts, "", false, true, true,false);
	}
	
	public static String generateXmlStyleString(String original){
		String[] parts = getParts(original);
		return generateStyledString(parts, "-", false, true, false,false);
	}
	
	public static String generateStyledString(String[] parts,String sep,boolean upper,boolean lower,boolean camel){
		return generateStyledString(parts, sep, upper, lower, camel, false);
	}
	
	public static String generateStyledString(String[] parts,String sep,boolean upper,boolean lower,boolean camel,boolean first){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<parts.length;i++){
			String p = parts[i];
			if(upper)p = p.toUpperCase();
			if(lower)p = p.toLowerCase();
			if( (camel && i>0) || first)p = p.substring(0, 1).toUpperCase()+p.substring(1);
			sb.append(p);
			if((i+1)<parts.length) sb.append(sep);
		}
		return sb.toString();
	}
	
	
	public static String[] getParts(String original){
		
		String[] parts = original.split(REGEX_NOT_FIRST_UC_CHAR+"|"+REGEX_SP_CHAR+"|"+REGEX_US_CHAR+"|"+REGEX_DASH_CHAR);
		List<String> keep = new ArrayList<String>();
		for(String part : parts){
			if(part != null && !part.equals("")) keep.add(part);
		}
		return keep.toArray(new String[keep.size()]);
	}
	
	
}
