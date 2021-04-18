package spel;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StringTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void stringLiteral() {
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String value = exp.getValue(String.class);
        assertThat(value, equalTo("Hello World!"));
    }

    @Test
    @Disabled("Throws SpelParseException")
    public void stringConstructor() {
        Expression exp = parser.parseExpression("${java.home}");
        String value = exp.getValue(String.class);
        assertThat(value, equalTo("Hello World"));
    }
}
