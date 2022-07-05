package pointcut.expression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Using ExpressionPointcut for declaring pointcuts.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AopConfiguration.class, StringService.class})
class ExpressionPointcutTest {

    @Autowired
    private StringService stringService;

    @Test
    void test() {
        assertThat(MyAdvice.invoked).isFalse();
        String upper = stringService.toUpperCase("lower");
        assertThat(MyAdvice.invoked).isTrue();
        assertThat(upper).isEqualTo("LOWER");
    }
}
