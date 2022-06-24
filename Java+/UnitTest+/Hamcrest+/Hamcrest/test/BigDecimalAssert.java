import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

/**
 * Проверка файлов.
 */
public class BigDecimalAssert {

    /**
     * Сравнение нуля с разным количеством знаков после запятой.
     */
    @Test
    void zero() {
        assertThat(new BigDecimal(0), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal(0.0), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal("0"), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal("0.0"), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal("0.00"), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
    }
}