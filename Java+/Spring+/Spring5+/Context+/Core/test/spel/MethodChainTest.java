package spel;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodChainTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void properties() {
        Expression exp = parser.parseExpression("'Hello World'.bytes.length");
        Integer value = (Integer) exp.getValue();
        assertThat(value, equalTo(11));
    }

    @Test
    public void method() {
        Expression exp = parser.parseExpression("new String('hello world').toUpperCase()");
        String value = (String) exp.getValue();
        assertThat(value, equalTo("HELLO WORLD"));
    }
}
