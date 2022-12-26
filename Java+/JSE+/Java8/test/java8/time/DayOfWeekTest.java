package java8.time;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class DayOfWeekTest {
    @Test
    void toStr() {
        var dateTime = LocalDate.parse("2015-03-25");
        var dayOfWeek = dateTime.getDayOfWeek();
        assertThat(dayOfWeek).hasToString("WEDNESDAY");
    }

    @Test
    void displayName() {
        var day = DayOfWeek.FRIDAY;
        assertThat(day.getDisplayName(TextStyle.NARROW_STANDALONE, Locale.ENGLISH)).isEqualTo("F");
        assertThat(day.getDisplayName(TextStyle.NARROW, Locale.ENGLISH)).isEqualTo("F");
        assertThat(day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)).isEqualTo("Fri");
        assertThat(day.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH)).isEqualTo("Fri");
        assertThat(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH)).isEqualTo("Friday");
        assertThat(day.getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH)).isEqualTo("Friday");
    }
}