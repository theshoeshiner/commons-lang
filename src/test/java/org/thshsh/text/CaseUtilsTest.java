package org.thshsh.text;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseUtilsTest {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CaseUtilsTest.class);

	@Test
	public void test() {

		assertAllRecursive("myVarName","my-var-name","MY-VAR-NAME","MyVarName","My Var Name","my_var_name","MY_VAR_NAME");


	}

	public void assertAllRecursive(String camel,String kebab,String kebabupper,String pascal,String pascalspace,String snake,String snakeupper) {
		assertAll(camel,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(kebab,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(kebabupper,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(pascal,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(pascalspace,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(snake,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
		assertAll(snakeupper,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper);
	}

	public void assertAll(String original,String camel,String kebab,String kebabupper,String pascal,String pascalspace,String snake,String snakeupper) {
		LOGGER.info("Checking {}",original);
		Assert.assertEquals(camel, CaseUtils.toCamelCase(original));
		Assert.assertEquals(kebab, CaseUtils.toKebabCase(original));
		Assert.assertEquals(pascal, CaseUtils.toPascalCase(original));
		Assert.assertEquals(pascalspace, CaseUtils.toPascalCase(original,true));
		Assert.assertEquals(snake, CaseUtils.toSnakeCase(original));

	}

}
