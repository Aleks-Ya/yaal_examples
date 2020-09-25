package spel.parser;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import spel.Inventor;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Executing SpEL without run Spring (with additional objects).
 */
public class EvaluationContextTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * Create EvaluationContext explicitly.
     */
    @Test
    public void explicitly() {
        Inventor tesla = new Inventor("Tesla");
        EvaluationContext context = new StandardEvaluationContext(tesla);

        Expression exp = parser.parseExpression("name");
        String value = (String) exp.getValue(context);

        assertThat(value, equalTo("Tesla"));
    }

    /**
     * Create EvaluationContext implicitly.
     */
    @Test
    public void implicitly() {
        Inventor tesla = new Inventor("Tesla");

        Expression exp = parser.parseExpression("name");
        String value = (String) exp.getValue(tesla);

        assertThat(value, equalTo("Tesla"));
    }

}
