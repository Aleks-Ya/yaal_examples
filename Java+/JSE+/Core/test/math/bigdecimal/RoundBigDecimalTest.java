package math.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

class RoundBigDecimalTest {
    @Test
    void scale() {
        var num = BigDecimal.valueOf(100.236);
        assertThat(num.toString()).isEqualTo("100.236");

        assertThat(num.setScale(1, RoundingMode.HALF_UP).toString()).isEqualTo("100.2");
        assertThat(num.setScale(2, RoundingMode.HALF_UP).toString()).isEqualTo("100.24");
    }

    @Test
    void scale2() {
        var num = BigDecimal.valueOf(100.235);
        assertThat(num.toString()).isEqualTo("100.235");

        assertThat(num.setScale(1, RoundingMode.HALF_UP).toString()).isEqualTo("100.2");
        assertThat(num.setScale(2, RoundingMode.HALF_UP).toString()).isEqualTo("100.24");
    }

    @Test
    void round() {
        var num = BigDecimal.valueOf(100.234);
        num = num.round(new MathContext(4));
        assertThat(num.toString()).isEqualTo("100.2");
    }

    @Test
    void addAndRound() {
        var d1 = BigDecimal.valueOf(100.234);
        var d2 = BigDecimal.valueOf(100.345);
        var sum = d1.add(d2, new MathContext(4));
        assertThat(sum.toString()).isEqualTo("200.6");
    }
}
