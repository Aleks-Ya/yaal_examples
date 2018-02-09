package spel.parser;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Executing SpEL without run Spring.
 */
public class ExpressionParserTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void string() {
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String value = (String) exp.getValue();
        assertThat(value, equalTo("Hello World!"));
    }

    @Test
    public void splitByComma() {
        Expression exp = parser.parseExpression("'Hello,World'.split(',')");
        String[] value = (String[]) exp.getValue();
        assertThat(value, arrayContaining("Hello", "World"));
    }

    @Test
    public void bytes() {
        Expression exp = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();
        assertThat(bytes, equalTo("Hello World".getBytes()));
    }
}
