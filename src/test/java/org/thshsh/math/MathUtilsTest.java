package org.thshsh.math;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {
	
	@Test
	public void test() {
		Assert.assertEquals(2,MathUtils.significantDigits("01.0"));
		Assert.assertEquals(2,MathUtils.significantDigits("01.20"));
		Assert.assertEquals(2,MathUtils.significantDigits("001.200"));
		Assert.assertEquals(4,MathUtils.significantDigits("101.20"));
		Assert.assertEquals(6,MathUtils.significantDigits("101.202"));
		Assert.assertEquals(4,MathUtils.significantDigits("101.0"));
		Assert.assertEquals(3,MathUtils.significantDigits("101"));
		Assert.assertEquals(3,MathUtils.significantDigits("1010"));
		Assert.assertEquals(3,MathUtils.significantDigits("001010"));
		Assert.assertEquals(4,MathUtils.significantDigits("001234000"));
		Assert.assertEquals(2,MathUtils.significantDigits("0.0024"));
		Assert.assertEquals(2,MathUtils.significantDigits("0.24"));
	}

}
