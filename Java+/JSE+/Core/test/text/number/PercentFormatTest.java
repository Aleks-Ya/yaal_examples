package text.number;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PercentFormatTest {
    private static final Locale ruRu = new Locale("ru", "RU");

    @Test
    void standard() {
        var format = NumberFormat.getPercentInstance(ruRu);
        assertThat(format.format(0.13), equalTo("13 %"));
        assertThat(format.format(65.5), equalTo("6 550 %"));
        assertThat(format.format(0.1234), equalTo("12 %"));
    }

    @Test
    void maximumFractionDigits() {
        var format = NumberFormat.getPercentInstance(ruRu);
        format.setMaximumFractionDigits(3);
        format.setMinimumFractionDigits(2);
        assertThat(format.format(0.13), equalTo("13,00 %"));
        assertThat(format.format(65.5), equalTo("6 550,00 %"));
        assertThat(format.format(0.1234567), equalTo("12,346 %"));
    }

    @Test
    void stringFormat() {
        var percent = 0.135;
        var actualResult = MessageFormat.format("{0,number,#.##%}", percent);
        assertThat(actualResult, equalTo("13.5%"));
    }
}
