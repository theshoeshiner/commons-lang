package org.thshsh.text;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseUtilsTest {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CaseUtilsTest.class);

	@Test
	public void test() {

		assertAllRecursive("myVarName","my-var-name","MY-VAR-NAME","MyVarName","My Var Name","my_var_name","MY_VAR_NAME");
				
	}
	
	@Test
	public void testWithNumber() {
		
		assertAllRecursive("myV4rName","my-v4r-name","MY-V4R-NAME","MyV4rName","My V4r Name","my_v4r_name","MY_V4R_NAME");
		
	}
	
	@Test
	public void testSpecial() {
		String out = CaseUtils.toSnakeCase("g/dL".replaceAll("\\W", "_").toUpperCase());
		LOGGER.info("out: {}",out);
		
		
	}
	
	@Test
	public void testTransformer() {

		String source = "with-id";
		Consumer<String[]> t = parts -> {
			for(int i=0;i<parts.length;i++) {
				if(parts[i].equalsIgnoreCase("id")) parts[i] = "ID";
			}
		};
		Assert.assertEquals("WithID", CaseUtils.toPascalCase(source,t));
		Assert.assertEquals("withID", CaseUtils.toCamelCase(source,t));
		
	}

	public void assertAllRecursive(String camel,String kebab,String kebabupper,String pascal,String pascalspace,String snake,String snakeupper) {
		/*assertAll(camel,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(kebab,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(kebabupper,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(pascal,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(pascalspace,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(snake,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(snakeupper,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);*/
		AssertionError error = null;
		for(String s : Arrays.asList(camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper)) {
			try {
				assertAll(s,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
			}
			catch(ComparisonFailure ae) {
				error = ae;
			}
		}
		
		if(error != null) {
			throw error;
		}
	}

	public void assertAll(String original,String camel,String kebab,String kebabupper,String pascal,String pascalspace,String snake,String snakeupper) {
		
		LOGGER.info("Checking {}",original);
		
		List<Function<String,String>> funcs = Arrays.asList(
				CaseUtils::toCamelCase,
				CaseUtils::toKebabCase,
				CaseUtils::toPascalCase,
				CaseUtils::toPascalCaseWithSpaces,
				CaseUtils::toSnakeCase
				);
				
		
		List<String> results = Arrays.asList(camel,kebab,pascal,pascalspace,snake);
		//BiFunction<String,Consumer<String[]>,String> f = CaseUtils::toCamelCase;	
		AssertionError error = null;
		for(int i=0;i<results.size();i++) {
			try {
				Function<String,String> f = funcs.get(i);
				String r = results.get(i);
				String ret = f.apply(original);
				//LOGGER.info("Assert: {} = {}",r,ret);
				Assert.assertEquals(r, ret);
			}
			catch(ComparisonFailure ae) {
				//LOGGER.info("caught error");
				error = ae;
			}
		}
		
		if(error != null) {
			throw error;
		}
		
		/*try {Assert.assertEquals(camel, CaseUtils.toCamelCase(original,t));}
		catch(AssertionError ae) {}
		Assert.assertEquals(kebab, CaseUtils.toKebabCase(original,t));
		Assert.assertEquals(pascal, CaseUtils.toPascalCase(original,t));
		Assert.assertEquals(pascalspace, CaseUtils.toPascalCase(original,true,t));
		Assert.assertEquals(snake, CaseUtils.toSnakeCase(original,t));*/

	}

}
