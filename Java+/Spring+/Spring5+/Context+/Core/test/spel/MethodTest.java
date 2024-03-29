package spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.assertj.core.api.Assertions.assertThat;

class MethodTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    void properties() {
        var exp = parser.parseExpression("'Hello World'.bytes.length");
        var value = (Integer) exp.getValue();
        assertThat(value).isEqualTo(11);
    }

    @Test
    void instanceMethod() {
        var exp = parser.parseExpression("new String('hello world').toUpperCase()");
        var value = (String) exp.getValue();
        assertThat(value).isEqualTo("HELLO WORLD");
    }

    @Test
    void staticMethod() {
        var exp = parser.parseExpression("T(java.util.UUID).randomUUID().toString()");
        var value = (String) exp.getValue();
        assertThat(value).hasSize(36);
    }
}
