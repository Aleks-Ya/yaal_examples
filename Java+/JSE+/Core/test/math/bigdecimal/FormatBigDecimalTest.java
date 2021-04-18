package math.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FormatBigDecimalTest {
    @Test
    public void withPattern() {
        BigDecimal num = BigDecimal.valueOf(10000000.2360000);
        DecimalFormat df = new DecimalFormat("#,###.00");
        String str = df.format(num);
        assertThat(str, equalTo("10,000,000.24"));
    }

    @Test
    public void withLocale() {
        BigDecimal num = BigDecimal.valueOf(10000000.236000000);
        Locale locale = new Locale("ru", "RU");
        NumberFormat df = DecimalFormat.getInstance(locale);
        String str = df.format(num);
        assertThat(str, equalTo("10 000 000,236"));
    }

}
