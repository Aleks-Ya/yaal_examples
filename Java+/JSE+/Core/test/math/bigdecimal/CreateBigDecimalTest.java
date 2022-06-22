package math.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Нужно использовать BigDecimal#valueOf вместо конструктора.
 */
class CreateBigDecimalTest {
    @Test
    void valueOf() {
        assertThat(BigDecimal.valueOf(100.236).toString()).isEqualTo("100.236");
    }

    @Test
    void constructor() {
        assertThat(new BigDecimal(100.236).toString()).isEqualTo("100.2360000000000042064129956997931003570556640625");
        assertThat(new BigDecimal("100.236").toString()).isEqualTo("100.236");
    }
}
