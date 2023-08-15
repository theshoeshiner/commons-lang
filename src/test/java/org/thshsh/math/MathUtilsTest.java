package org.thshsh.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathUtilsTest {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(MathUtilsTest.class);

	
	@Test
	public void test() {
		
		Assert.assertEquals(3,MathUtils.countSignificantFigures("00.0"));
		
		Assert.assertEquals(2,MathUtils.countSignificantFigures("01.0"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("01.20"));
		Assert.assertEquals(4,MathUtils.countSignificantFigures("001.200"));
		Assert.assertEquals(5,MathUtils.countSignificantFigures("101.20"));
		Assert.assertEquals(6,MathUtils.countSignificantFigures("101.202"));
		Assert.assertEquals(4,MathUtils.countSignificantFigures("101.0"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("101"));
		Assert.assertEquals(1,MathUtils.countSignificantFigures("100"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("100."));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("1010"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("001010"));
		Assert.assertEquals(4,MathUtils.countSignificantFigures("001234000"));
		Assert.assertEquals(2,MathUtils.countSignificantFigures("0.0024"));
		Assert.assertEquals(2,MathUtils.countSignificantFigures("0.24"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("0.240"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("000.240"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("000.000240"));
		Assert.assertEquals(7,MathUtils.countSignificantFigures("1000.240"));
		Assert.assertEquals(1,MathUtils.countSignificantFigures("0"));
		Assert.assertEquals(1,MathUtils.countSignificantFigures("0000"));
		Assert.assertEquals(1,MathUtils.countSignificantFigures("."));
		Assert.assertEquals(2,MathUtils.countSignificantFigures("0.0"));
		Assert.assertEquals(4,MathUtils.countSignificantFigures("0.000"));
		Assert.assertEquals(1,MathUtils.countSignificantFigures("0.001"));
		Assert.assertEquals(1,MathUtils.countSignificantFigures("0"));
		Assert.assertEquals(4,MathUtils.countSignificantFigures("00.00"));
		Assert.assertEquals(1,MathUtils.countSignificantFigures("0e1"));
		Assert.assertEquals(3,MathUtils.countSignificantFigures("0.00e11"));
		
	}
	
	@Test
	public void testIllegal() {
		Assert.assertEquals(1,MathUtils.countSignificantFigures("12ff"));
	}

	@Test
	public void testBigDecimal() {
		Number n;
		//BigDecimal bd = new BigDecimal("10000000");
		//bd.setScale(3);
		BigDecimal bd = new BigDecimal("999.0").add(new BigDecimal("9.41"));
		LOGGER.info("bd: {}",bd);
		LOGGER.info("bd: {}",bd.precision());
		LOGGER.info("bd: {}",bd.scale());
		LOGGER.info("bd: {}",bd.unscaledValue());
		LOGGER.info("bd: {}",bd.setScale(1,RoundingMode.HALF_EVEN));
		
		
		
		BigDecimal rep = new BigDecimal("10000");
		//rep = rep.divide(new BigDecimal("3"));
		
		
	}

}
