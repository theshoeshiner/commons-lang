package org.thshsh.text;

public class WordUtils {
	
	protected static String[] ENGLISH_VOWELS = new String[]{"a","e","i","o","u"};
	protected static String[] ENGLISH_VOWELS_UPPER = new String[]{"A","E","I","O","U"};
	protected static String ENGLISH_VOWELS_REGEX = "[aeiouAEIOU]";
	
	public static String abbreviate(String word){
		String first = word.substring(0,1);
		String temp = word.substring(1);
		temp = temp.replaceAll(ENGLISH_VOWELS_REGEX, "");
		temp = first + temp;
		return temp;
	}

}
