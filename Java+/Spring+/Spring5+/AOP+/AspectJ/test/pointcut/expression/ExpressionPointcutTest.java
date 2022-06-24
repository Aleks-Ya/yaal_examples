package pointcut.expression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Using ExpressionPointcut for declaring pointcuts.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AopConfiguration.class, StringService.class})
public class ExpressionPointcutTest {

    @Autowired
    private StringService stringService;

    @Test
    void test() {
        assertFalse(MyAdvice.invoked);
        String upper = stringService.toUpperCase("lower");
        assertTrue(MyAdvice.invoked);
        assertThat(upper, equalTo("LOWER"));
    }
}
