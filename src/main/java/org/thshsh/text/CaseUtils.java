package org.thshsh.text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseUtils {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CaseUtils.class);


	//Splits on caps, except first, so it works for camel case and pascal case
	//This will combine strings of caps like abbreviations, 
	//but that breaks camel case strings which consider multiple caps to be separate tokens
	public static String REGEX_NOT_FIRST_UC_CHAR = "(?<=.)(?<!([A-Z0-9]))(?=([A-Z]+))";
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
		return toPascalCase(original, false,null);
	}
	
	public static String toPascalCase(String original,Consumer<String[]> transform){
		return toPascalCase(original, false,transform);
	}
	
	public static String toPascalCase(String original,Boolean space){
		return toPascalCase(original, space,null);
	}

	public static String toPascalCaseWithSpaces(String original){
		return toPascalCase(original, true,null);
	}
	
	public static String toPascalCase(String original,Boolean space,Consumer<String[]> transform){
		return generateCasedString(original,transform, space?SPACE_CHAR:EMPTY_CHAR, false, false, true,true);
	}


	public static String toSnakeCase(String original){
		return generateCasedString(original,null, UNDERSCORE_CHAR, false, true, false,false);
	}
	
	public static String toSnakeCase(String original,Consumer<String[]> transform){
		return generateCasedString(original,transform, UNDERSCORE_CHAR, false, true, false,false);
	}

	public static String toCamelCase(String original){
		return generateCasedString(original,null, EMPTY_CHAR, false, true, true,false);
	}
	
	public static String toCamelCase(String original,Consumer<String[]> transform){
		return generateCasedString(original,transform, EMPTY_CHAR, false, true, true,false);
	}

	public static String toKebabCase(String original){
		return generateCasedString(original,null, DASH_CHAR, false, true, false,false);
	}
	
	public static String toKebabCase(String original,Consumer<String[]> transform){
		return generateCasedString(original,transform, DASH_CHAR, false, true, false,false);
	}


	public static String generateCasedString(String original,Consumer<String[]> transform,String sep,boolean upper,boolean lower,boolean camel,boolean first){
		String[] parts = getParts(original);
		return generateCasedString(parts, transform,sep, upper, lower, camel, first);
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
	public static String generateCasedString(String[] parts,Consumer<String[]> transform,String sep,boolean upper,boolean lower,boolean camel,boolean first){
		
		for(int i=0;i<parts.length;i++){
			String p = parts[i];
			if(upper)p = p.toUpperCase();
			if(lower)p = p.toLowerCase();
			if( (camel && i>0) || first)p = p.substring(0, 1).toUpperCase()+p.substring(1).toLowerCase();
			parts[i] = p;
		}

		if(transform!=null) transform.accept(parts);
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<parts.length;i++){
			String p = parts[i];
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
		LOGGER.trace("Tokens: {}",keep);
		return keep.toArray(new String[keep.size()]);
	}


}
