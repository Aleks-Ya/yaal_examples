package math.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class FormatBigDecimalTest {
    @Test
    void withPattern() {
        var num = BigDecimal.valueOf(10000000.2360000);
        var df = new DecimalFormat("#,###.00");
        var str = df.format(num);
        assertThat(str, equalTo("10,000,000.24"));
    }

    @Test
    void withLocale() {
        var num = BigDecimal.valueOf(10000000.236000000);
        var locale = new Locale("ru", "RU");
        var df = DecimalFormat.getInstance(locale);
        var str = df.format(num);
        assertThat(str, equalTo("10 000 000,236"));
    }

}
