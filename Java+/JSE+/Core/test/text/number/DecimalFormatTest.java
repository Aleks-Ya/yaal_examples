package text.number;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DecimalFormatTest {
    private static final Locale ruRu = new Locale("ru", "RU");

    @Test
    public void noEndingZeros() {
        DecimalFormat format = new DecimalFormat("###.##", DecimalFormatSymbols.getInstance(ruRu));

        assertThat(format.format(100), equalTo("100"));
        assertThat(format.format(10.5), equalTo("10,5"));
        assertThat(format.format(12345.5), equalTo("12345,5"));
    }

    @Test
    public void endingZeros() {
        DecimalFormat format = new DecimalFormat("###.00", DecimalFormatSymbols.getInstance(ruRu));

        assertThat(format.format(100), equalTo("100,00"));
        assertThat(format.format(10.5), equalTo("10,50"));
        assertThat(format.format(12345.5), equalTo("12345,50"));
    }
}
