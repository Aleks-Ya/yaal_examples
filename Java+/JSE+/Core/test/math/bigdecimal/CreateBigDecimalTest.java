package math.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Нужно использовать BigDecimal#valueOf вместо конструктора.
 */
public class CreateBigDecimalTest {
    @Test
    public void valueOf() {
        assertThat(BigDecimal.valueOf(100.236).toString(), equalTo("100.236"));
    }

    @Test
    public void constructor() {
        assertThat(new BigDecimal(100.236).toString(), equalTo("100.2360000000000042064129956997931003570556640625"));
        assertThat(new BigDecimal("100.236").toString(), equalTo("100.236"));
    }
}
