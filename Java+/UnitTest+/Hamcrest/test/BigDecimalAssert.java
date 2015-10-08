import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Проверка файлов.
 */
public class BigDecimalAssert {

    /**
     * Сравнение нуля с разным количеством знаков после запятой.
     */
    @Test
    public void zero() {
        assertThat(new BigDecimal(0), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal(0.0), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal("0"), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal("0.0"), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
        assertThat(new BigDecimal("0.00"), closeTo(BigDecimal.ZERO, BigDecimal.ZERO));
    }
}