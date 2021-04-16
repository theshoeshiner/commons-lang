package org.thshsh.text;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseUtils {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CaseUtils.class);


	//Splits on caps, except first, so it works for camel case and proper case
	//This will combine strings of caps like abbreviations, but that breaks camel case strings
	public static String REGEX_NOT_FIRST_UC_CHAR = "(?<=.)(?<!(\\p{Upper}))(?=(\\p{Upper}+))";
	//splits on whitespace
	public static String REGEX_SP_CHAR = "(\\s++)";
	//splits on underscore
	public static String REGEX_US_CHAR = "[_]";
	//splits on dash
	public static String REGEX_DASH_CHAR = "[-]";

	public static String SPACE_CHAR = " ";
	public static String EMPTY_CHAR = "";
	public static String DASH_CHAR = "-";
	public static String UNDERSCORE_CHAR = "_";

	public static String toPascalCase(String original){
		return toPascalCase(original, false);
	}

	public static String toPascalCase(String original,Boolean space){
		String[] parts = getParts(original);
		return generateCasedString(parts, space?SPACE_CHAR:EMPTY_CHAR, false, false, true,true);
	}


	public static String toSnakeCase(String original){
		String[] parts = getParts(original);
		return generateCasedString(parts, UNDERSCORE_CHAR, false, true, false,false);
	}

	public static String toCamelCase(String original){
		String[] parts = getParts(original);
		return generateCasedString(parts, EMPTY_CHAR, false, true, true,false);
	}

	public static String toKebabCase(String original){
		String[] parts = getParts(original);
		return generateCasedString(parts, DASH_CHAR, false, true, false,false);
	}

	public static String generateCasedString(String[] parts,String sep,boolean upper,boolean lower,boolean camel){
		return generateCasedString(parts, sep, upper, lower, camel, false);
	}

	/**
	 *
	 * @param parts
	 * @param sep
	 * @param upper
	 * @param lower
	 * @param camel - means that we are making all parts after the first start with capital eg: Test
	 * @param first
	 * @return
	 */
	public static String generateCasedString(String[] parts,String sep,boolean upper,boolean lower,boolean camel,boolean first){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<parts.length;i++){
			String p = parts[i];
			if(upper)p = p.toUpperCase();
			if(lower)p = p.toLowerCase();
			if( (camel && i>0) || first)p = p.substring(0, 1).toUpperCase()+p.substring(1).toLowerCase();
			sb.append(p);
			if((i+1)<parts.length) sb.append(sep);
		}
		return sb.toString();
	}


	public static String[] getParts(String original){

		String[] parts = original.split(REGEX_NOT_FIRST_UC_CHAR+"|"+REGEX_SP_CHAR+"|"+REGEX_US_CHAR+"|"+REGEX_DASH_CHAR);
		List<String> keep = new ArrayList<String>();
		for(String part : parts){
			if(part != null && !part.equals(EMPTY_CHAR)) keep.add(part);
		}
		return keep.toArray(new String[keep.size()]);
	}


}
