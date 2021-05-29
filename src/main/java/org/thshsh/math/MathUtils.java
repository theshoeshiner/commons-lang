package org.thshsh.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MathUtils.class);
	
	public static final char ZERO = '0';
	public static final char DECIMAL = '.';
	
	/**
	 * This doesn't check for anything other than zeros and decimals and is thus dependent 
	 * on the user to ensure that the parameter is a correctly formatted decimal number
	 * @param decimalNumber
	 * @return
	 */
	public static int significantDigits(String decimalNumber) {
		//track for leading zeros
		boolean foundSig = false;
		boolean decimal = false;
		//track count of zeros to add if they are between sigfigs
		int zeros = 0;
		int sig = 0;
		for(char c : decimalNumber.toCharArray()) {
			if(c == ZERO) {
				if(foundSig) zeros++;
			}
			else if(c == DECIMAL) {
				decimal = true;
			}
			else {
				decimal = false;
				foundSig = true;
				sig++;
				sig+=zeros;
				zeros=0;
			}
		}
		//if we found no sigfigs after the decimal then count the decimal
		if(decimal) sig++;
		return sig;
	}

}
