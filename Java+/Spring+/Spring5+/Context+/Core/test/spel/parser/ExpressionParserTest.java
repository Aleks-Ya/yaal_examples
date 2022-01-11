package spel.parser;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Executing SpEL without run Spring.
 */
class ExpressionParserTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    void string() {
        var exp = parser.parseExpression("'Hello World'.concat('!')");
        var value = (String) exp.getValue();
        assertThat(value).isEqualTo("Hello World!");
    }

    @Test
    void splitByComma() {
        var exp = parser.parseExpression("'Hello,World'.split(',')");
        var value = (String[]) exp.getValue();
        assertThat(value).contains("Hello", "World");
    }

    @Test
    void bytes() {
        var exp = parser.parseExpression("'Hello World'.bytes");
        var bytes = (byte[]) exp.getValue();
        assertThat(bytes).asString().isEqualTo("Hello World");
    }
}
