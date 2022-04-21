package math.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CompareBigDecimalTest {
    @Test
    void compare() {
        var d1 = new BigDecimal("100.2");
        var d2 = new BigDecimal("100.2");
        assertThat(d1).isEqualTo(d2);
    }

    @Test
    void compareTo1() {
        var d1 = new BigDecimal("100.2");
        var d2 = new BigDecimal("50.1").add(new BigDecimal("50.1"));
        assertThat(d1).isEqualTo(d2);
        assertThat(d1).isEqualByComparingTo(d2);
    }

    @Test
    void compareToVsEquals() {
        var d1 = new BigDecimal("2");
        var d2 = new BigDecimal("2.0");
        assertThat(d1).isNotEqualTo(d2);
        assertThat(d1.compareTo(d2)).isEqualTo(0);
    }
}
