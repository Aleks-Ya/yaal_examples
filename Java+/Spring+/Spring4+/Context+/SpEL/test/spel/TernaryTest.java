package spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TernaryTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void full() {
        Expression exp = parser.parseExpression("false ? 'trueExp' : 'falseExp'");
        String value = exp.getValue(String.class);
        assertThat(value, equalTo("falseExp"));
    }

    @Test
    public void elvisString() {
        Inventor tesla = new Inventor((String) null);
        Expression exp = parser.parseExpression("name?:'Unknown'");
        String value = (String) exp.getValue(tesla);
        assertThat(value, equalTo("Unknown"));
    }

    @Test
    public void elvisInt() {
        Inventor tesla = new Inventor((Integer) null);
        Expression exp = parser.parseExpression("id?:2");
        Integer value = (Integer) exp.getValue(tesla);
        assertThat(value, equalTo(2));
    }
}
