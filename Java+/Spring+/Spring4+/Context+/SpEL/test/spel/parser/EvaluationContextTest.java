package spel.parser;

import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import spel.Inventor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Executing SpEL without run Spring (with additional objects).
 */
class EvaluationContextTest {
    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * Create EvaluationContext explicitly.
     */
    @Test
    void explicitly() {
        var tesla = new Inventor("Tesla");
        EvaluationContext context = new StandardEvaluationContext(tesla);

        var exp = parser.parseExpression("name");
        var value = (String) exp.getValue(context);

        assertThat(value).isEqualTo("Tesla");
    }

    /**
     * Create EvaluationContext implicitly.
     */
    @Test
    void implicitly() {
        var tesla = new Inventor("Tesla");

        var exp = parser.parseExpression("name");
        var value = (String) exp.getValue(tesla);

        assertThat(value).isEqualTo("Tesla");
    }

}
