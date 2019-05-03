package spel;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class StringTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void stringLiteral() {
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String value = exp.getValue(String.class);
        assertThat(value, equalTo("Hello World!"));
    }

    @Test
    @Ignore("Throws SpelParseException")
    public void stringConstructor() {
        Expression exp = parser.parseExpression("${java.home}");
        String value = exp.getValue(String.class);
        assertThat(value, equalTo("Hello World"));
    }
}
