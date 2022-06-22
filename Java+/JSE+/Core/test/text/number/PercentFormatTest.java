package text.number;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class PercentFormatTest {
    private static final Locale ruRu = new Locale("ru", "RU");

    @Test
    void standard() {
        var format = NumberFormat.getPercentInstance(ruRu);
        assertThat(format.format(0.13)).isEqualTo("13 %");
        assertThat(format.format(65.5)).isEqualTo("6 550 %");
        assertThat(format.format(0.1234)).isEqualTo("12 %");
    }

    @Test
    void maximumFractionDigits() {
        var format = NumberFormat.getPercentInstance(ruRu);
        format.setMaximumFractionDigits(3);
        format.setMinimumFractionDigits(2);
        assertThat(format.format(0.13)).isEqualTo("13,00 %");
        assertThat(format.format(65.5)).isEqualTo("6 550,00 %");
        assertThat(format.format(0.1234567)).isEqualTo("12,346 %");
    }

    @Test
    void stringFormat() {
        var percent = 0.135;
        var actualResult = MessageFormat.format("{0,number,#.##%}", percent);
        assertThat(actualResult).isEqualTo("13.5%");
    }
}
