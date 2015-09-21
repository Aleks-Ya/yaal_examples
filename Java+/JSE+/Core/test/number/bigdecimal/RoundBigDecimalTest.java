package number.bigdecimal;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RoundBigDecimalTest {
    @Test
    public void scale() {
        BigDecimal num = BigDecimal.valueOf(100.236);
        assertThat(num.toString(), equalTo("100.236"));

        assertThat(num.setScale(1, BigDecimal.ROUND_HALF_UP).toString(), equalTo("100.2"));
        assertThat(num.setScale(2, BigDecimal.ROUND_HALF_UP).toString(), equalTo("100.24"));
    }

    @Test
    public void scale2() {
        BigDecimal num = BigDecimal.valueOf(100.235);
        assertThat(num.toString(), equalTo("100.235"));

        assertThat(num.setScale(1, BigDecimal.ROUND_HALF_UP).toString(), equalTo("100.2"));
        assertThat(num.setScale(2, BigDecimal.ROUND_HALF_UP).toString(), equalTo("100.24"));
    }

    @Test
    public void round() {
        BigDecimal num = BigDecimal.valueOf(100.234);
        num = num.round(new MathContext(4));

        assertThat(num.toString(), equalTo("100.2"));
    }

    @Test
    public void addAndRound() {
        BigDecimal d1 = BigDecimal.valueOf(100.234);
        BigDecimal d2 = BigDecimal.valueOf(100.345);
        BigDecimal sum = d1.add(d2, new MathContext(4));

        assertThat(sum.toString(), equalTo("200.6"));
    }
}
