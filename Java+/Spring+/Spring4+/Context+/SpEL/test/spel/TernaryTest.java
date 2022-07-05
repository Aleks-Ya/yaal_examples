package spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.assertj.core.api.Assertions.assertThat;

class TernaryTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    void full() {
        var exp = parser.parseExpression("false ? 'trueExp' : 'falseExp'");
        var value = exp.getValue(String.class);
        assertThat(value).isEqualTo("falseExp");
    }

    @Test
    void elvisString() {
        var tesla = new Inventor((String) null);
        var exp = parser.parseExpression("name?:'Unknown'");
        var value = (String) exp.getValue(tesla);
        assertThat(value).isEqualTo("Unknown");
    }

    @Test
    void elvisInt() {
        var tesla = new Inventor((Integer) null);
        var exp = parser.parseExpression("id?:2");
        var value = (Integer) exp.getValue(tesla);
        assertThat(value).isEqualTo(2);
    }
}
