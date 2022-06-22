package text.number;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class DecimalFormatTest {
    private static final Locale ruRu = new Locale("ru", "RU");

    @Test
    void noEndingZeros() {
        var format = new DecimalFormat("###.##", DecimalFormatSymbols.getInstance(ruRu));

        assertThat(format.format(100)).isEqualTo("100");
        assertThat(format.format(10.5)).isEqualTo("10,5");
        assertThat(format.format(12345.5)).isEqualTo("12345,5");
    }

    @Test
    void endingZeros() {
        var format = new DecimalFormat("###.00", DecimalFormatSymbols.getInstance(ruRu));

        assertThat(format.format(100)).isEqualTo("100,00");
        assertThat(format.format(10.5)).isEqualTo("10,50");
        assertThat(format.format(12345.5)).isEqualTo("12345,50");
    }
}
