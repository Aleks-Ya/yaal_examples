package spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.assertj.core.api.Assertions.assertThat;

class StringTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    void stringLiteral() {
        var exp = parser.parseExpression("'Hello World'.concat('!')");
        var value = exp.getValue(String.class);
        assertThat(value).isEqualTo("Hello World!");
    }

    @Test
    void stringConstructor() {
        var exp = parser.parseExpression("${java.home}");
        var value = exp.getValue(String.class);
        assertThat(value).isEqualTo("Hello World");
    }
}
