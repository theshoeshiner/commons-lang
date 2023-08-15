package org.thshsh.math;

import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathUtils {

	public static final Logger LOGGER = LoggerFactory.getLogger(MathUtils.class);

	/** Reusable char constant for zero. */
	public static final char CHAR_ZERO = '0';
	/** Reusable char constant for decimal separator character period. */
	public static final char CHAR_DECIMAL_PERIOD = '.';
	/** Reusable char constant for decimal separator character comma. */
	public static final char CHAR_DECIMAL_COMMA = ',';
	/** Reusable char constant for exponent character - lowercase */
	public static final char CHAR_EXPONENT_LOWER = 'e';
	/** Reusable char constant for exponent character - uppercase */
	public static final char CHAR_EXPONENT_UPPER = 'E';
	/** Reusable char constant for positive sign */
	public static final char CHAR_SIGN_POSITIVE = '+';
	/** Reusable char constant for negative sign */
	public static final char CHAR_SIGN_NEGATIVE = '-';

	/**
	 * Counts the number of significant figures within a decimal value string.
	 * {@link org.apache.commons.lang3.math.NumberUtils#countSignificantFigures(String, char)}
	 *
	 * @param decimalNumber The decimal number string. Must be numeric digits with
	 *        optional decimal and optional integer exponential suffix
	 * @return The number of significant figures in the string
	 */
	public static int countSignificantFigures(String decimalNumber) {
		return countSignificantFigures(decimalNumber, CHAR_DECIMAL_PERIOD);
	}

	/**
	 * Counts the number of significant figures within a decimal value string. This
	 * method must accept a string because significant figures can only be inferred
	 * from the a user provided representation, and not from a binary numerical
	 * value. This logic is useful/necessary when working with measured values where
	 * it is critical to retain the implied uncertainty in the initial value for use
	 * in formatting after calculations. <br>
	 * The rules that are followed, outlined below, are widely accepted standard
	 * conventions, discussed in many texts [1][2]. <b>Note:</b> Zero values are an
	 * exceptional case that is unfortunately not often discussed or handled. This
	 * implementation aims to retain the implied uncertainty and precision in a zero
	 * value such that further mathematical operations can continue to report it.
	 * The goal of this logic is to allow zero values to be reported with
	 * significant figure counts that match measurements in the same dataset. i.e.
	 * if the values 2.1 and 0.0 are measured on the same apparatus and reported in
	 * the same dataset, we should be able to convey that they both have 2
	 * significant figures. <br>
	 * <b>Note:</b> Rules 1-6 deal with the non-exponent portion of the string, and
	 * by rule apply only to characters prior to the optional exponent character
	 * ('e'|'E'). Rule 7 covers the exponential portion. <br>
	 * 1) All nonzero digits are always significant. <br>
	 * 2) Zeros that appear between other nonzero digits are called "middle zeros",
	 * and are always significant. <br>
	 * 3) Zeros that appear in front of all of the nonzero digits are called
	 * "leading zeros", and are never significant. <br>
	 * 4) Zeros that appear after all nonzero digits are called "trailing zeros". In
	 * a value with a decimal point they are significant. <br>
	 * 5) "Trailing zeros" in a value that does not contain a decimal are not
	 * significant. <br>
	 * 6) Zero values are strings that only contain zeros and an optional decimal.
	 * In these values, the first leading zero is considered to be significant, and
	 * all trailing zeros are handled as if the first were nonzero (See Examples
	 * Below). <br>
	 * <table border="1">
	 * <tr>
	 * <th>String</th>
	 * <th>Count</th>
	 * <th>Reason</th>
	 * <th>Note</th>
	 * </tr>
	 * <tr>
	 * <td>0.00</td>
	 * <td>3</td>
	 * <td>The first whole zero is significant (Rule 6), and the two trailing zeros
	 * are significant (Rule 4)</td>
	 * <td></td>
	 * </tr>
	 * <tr>
	 * <td>0e20</td>
	 * <td>1</td>
	 * <td>The first whole zero is significant (Rule 6) and the exponent is not
	 * (Rule 7)</td>
	 * <td></td>
	 * </tr>
	 * <tr>
	 * <td>0000</td>
	 * <td>1</td>
	 * <td>The first whole zero is significant (Rule 6) and the last three are not
	 * significant (Rule 5)</td>
	 * <td>This is an extreme case, documented here for clarity, uncommon in
	 * practice</td>
	 * </tr>
	 * <tr>
	 * <td>00.0</td>
	 * <td>3</td>
	 * <td>First whole zero is significant (Rule 6) and the last two are also
	 * significant (Rule
	 * <td></td> 4)</td>
	 * </tr>
	 * <tr>
	 * <td>0000.</td>
	 * <td>4</td>
	 * <td>First whole zero is significant (Rule 6) and the last three are also
	 * significant (Rule
	 * <td></td> 4)</td>
	 * </tr>
	 * </table>
	 * 7) Integers present after an optional 'e' character are the "exponent". These
	 * may only be decimal digit characters, and are never significant. <br>
	 * <br>
	 * [1] <a href=
	 * "https://chem.libretexts.org/Bookshelves/Introductory_Chemistry/Map%3A_Fundamentals_of_General_Organic_and_Biological_Chemistry_(McMurry_et_al.)/01%3A_Matter_and_Measurements/1.08%3A_Measurement_and_Significant_Figures">Measurement
	 * and Significant Figures</a> [2] <a href=
	 * "https://chemed.chem.purdue.edu/genchem/topicreview/bp/ch1/sigfigs.html">Significant
	 * Figures</a>
	 *
	 * @param decimalNumber The decimal number string. Must be optional sign +
	 *        numeric digits with optional decimal and optional e|E + sign + integer
	 *        exponential suffix
	 * @param decimalSeparator the decimal separator character to use
	 * @return The number of significant figures in the string
	 */
	public static int countSignificantFigures(String decimalNumber, char decimalSeparator) {
		if (decimalNumber.length() == 0) {
			throw new IllegalArgumentException("Decimal Number string was empty");
		}
		if (CharUtils.isAsciiNumeric(decimalSeparator)) {
			throw new IllegalArgumentException("Decimal Separator cannot be numeric");
		}
		if (decimalSeparator == CHAR_EXPONENT_UPPER || decimalSeparator == CHAR_EXPONENT_LOWER) {
			throw new IllegalArgumentException("Decimal Separator cannot be exponent character 'e'");
		}
		// track current part
		boolean foundNonZero = false;
		boolean rightSide = false;
		int exponentIndex = -1;
		// track count of zeros that are potential sigfigs
		int leadingZeros = 0;
		int middleZeros = 0;
		//track known sigfigs
		int sigFigs = 0;
		int index = 0;
		//skip first character if its the sign
		char firstChar = decimalNumber.charAt(0);
		if (firstChar == CHAR_SIGN_NEGATIVE || firstChar == CHAR_SIGN_POSITIVE) {
			index++;
		}
		for (; index < decimalNumber.length(); index++) {
			char c = decimalNumber.charAt(index);
			if (c == CHAR_ZERO) {
				if (!rightSide) {
					if (foundNonZero) {
						// whole trailing zeros MIGHT be middle zeros (Rule 2)
						middleZeros++;
					} else {
						//whole leading zeros might be middle zeros (Rule 6 / Rule 3)
						leadingZeros++;
					}
				} else {
					if (foundNonZero) {
						//decimal trailing zeros are always significant (Rule 4)
						sigFigs++;
					} else {
						// decimal leading zeros might be middle zeros (Rule 6 / Rule 2)
						middleZeros++;
					}
				}
			} else if (c == decimalSeparator) {
				if (rightSide) {
					throw new IllegalArgumentException("Illegal duplicate separator found at index " + index);
				}
				rightSide = true;
				// if a decimal is present, all whole trailing zeros are middle zeros and are
				// significant significant (Rule 4)
				sigFigs += middleZeros;
				middleZeros = 0;
			} else if (c == CHAR_EXPONENT_UPPER || c == CHAR_EXPONENT_LOWER) {
				// exponent is not significant and ends the parsing of sigfigs
				exponentIndex = index;
				if (exponentIndex == 0) {
					throw new IllegalArgumentException("Decimal part was empty");
				}
				break;
			} else if (CharUtils.isAsciiNumeric(c)) {
				// non zero digits are ALWAYS significant (Rule 1)
				sigFigs++;
				if (!foundNonZero) {
					// this was the first nonzero
					foundNonZero = true;
					// reset the zero count because the middle zeros were actually leading zeros
					middleZeros = 0;
				} else {
					// zeros between nonzeros are middle zeros and are always significant (Rule 2)
					sigFigs += middleZeros;
					middleZeros = 0;
				}
			} else {
				throw new IllegalArgumentException("Illegal character '" + c + "' at index " + index);
			}
		}
		// any remaining zeros were trailing and are not significant (Rule 5)

		// if we found no nonzeros, then this is a zero value measurement.
		if (!foundNonZero) {
			sigFigs += middleZeros;
			if (rightSide) {
				sigFigs += leadingZeros;
			} else {
				//only one leading zero is significant, others are trailing
				sigFigs += 1;
			}
		}

		if (exponentIndex > -1) {
			// Exponent is numeric digits only and not significant (Rule 7)
			if (exponentIndex + 1 == decimalNumber.length()) {
				throw new IllegalArgumentException("Exponent part was empty");
			}
			int expIndex = exponentIndex + 1;
			//skip first character if its the sign
			char firstExpChar = decimalNumber.charAt(expIndex);
			if (firstExpChar == CHAR_SIGN_NEGATIVE || firstExpChar == CHAR_SIGN_POSITIVE) {
				expIndex++;
			}
			for (int i = expIndex; i < decimalNumber.length(); i++) {
				char c = decimalNumber.charAt(i);
				if (!CharUtils.isAsciiNumeric(c)) {
					throw new IllegalArgumentException("Illegal character '" + c + "' at index " + i);
				}
			}
		}
		return sigFigs;
	}
}
