package org.thshsh.text.cases;

public class Cases {

    public static final KebabCase KEBAB = KebabCase.INSTANCE;
    public static final SnakeCase SNAKE = SnakeCase.INSTANCE;
    public static final CamelCase CAMEL = CamelCase.INSTANCE;
    public static final PascalCase PASCAL = PascalCase.INSTANCE;
    
    public static String convert(String string, Case from, Case to) {
        return to.format(from.parse(string));
    }
    
}
