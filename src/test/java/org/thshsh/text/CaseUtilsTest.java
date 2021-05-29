package org.thshsh.text;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseUtilsTest {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CaseUtilsTest.class);

	@Test
	public void test() {

		assertAllRecursive("myVarName","my-var-name","MY-VAR-NAME","MyVarName","My Var Name","my_var_name","MY_VAR_NAME",null);
		
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

	public void assertAllRecursive(String camel,String kebab,String kebabupper,String pascal,String pascalspace,String snake,String snakeupper,Consumer<String[]> t) {
		assertAll(camel,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper,t);
		assertAll(kebab,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper,t);
		assertAll(kebabupper,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper,t);
		assertAll(pascal,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper,t);
		assertAll(pascalspace,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper,t);
		assertAll(snake,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper,t);
		assertAll(snakeupper,camel,kebab,kebabupper,pascal,pascalspace,snake,snakeupper,t);
	}

	public void assertAll(String original,String camel,String kebab,String kebabupper,String pascal,String pascalspace,String snake,String snakeupper,Consumer<String[]> t) {
		LOGGER.info("Checking {}",original);
		Assert.assertEquals(camel, CaseUtils.toCamelCase(original,t));
		Assert.assertEquals(kebab, CaseUtils.toKebabCase(original,t));
		Assert.assertEquals(pascal, CaseUtils.toPascalCase(original,t));
		Assert.assertEquals(pascalspace, CaseUtils.toPascalCase(original,true,t));
		Assert.assertEquals(snake, CaseUtils.toSnakeCase(original,t));

	}

}
