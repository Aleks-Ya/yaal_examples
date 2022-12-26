package java8.time;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Форматирование дат.
 */
class FormatTest {
    @Test
    void dateTime() {
        var dateTime = LocalDateTime.parse("2015-03-25T10:15:30");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var str = dateTime.format(formatter);
        assertThat(str).isEqualTo("25.03.2015");
    }

    @Test
    void dateTimeDefault() {
        var str = "2015-03-25T10:15:30";
        var dateTime = LocalDateTime.parse(str);
        assertThat(dateTime.toString()).isEqualTo(str);
    }

    @Test
    void zonedDateTime() {
        var dateTime = ZonedDateTime.of(2015, 3, 25, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var str = dateTime.format(formatter);
        assertThat(str).isEqualTo("25.03.2015");
    }

    @Test
    void localDate() {
        var localDate = LocalDate.parse("2015-03-25");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyyEEE");
        var str = localDate.format(formatter);
        assertThat(str).isEqualTo("25.03.2015Wed");
    }

    @Test
    void dayOfWeek() {
        var day = DayOfWeek.SATURDAY;
        assertThat(DateTimeFormatter.ofPattern("E").format(day)).isEqualTo("Sat");
        assertThat(DateTimeFormatter.ofPattern("EE").format(day)).isEqualTo("Sat");
        assertThat(DateTimeFormatter.ofPattern("EEE").format(day)).isEqualTo("Sat");
        assertThat(DateTimeFormatter.ofPattern("EEEE").format(day)).isEqualTo("Saturday");
        assertThat(DateTimeFormatter.ofPattern("EEEEE").format(day)).isEqualTo("S");
    }

    @Test
    void instant() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(ZoneId.of("Europe/Moscow"));
        var str = formatter.format(instant);
        assertThat(str).isEqualTo("25.03.2007 14:15:30");
    }

    @Test
    void instantWithTimezone() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss O z VV").withZone(ZoneId.of("Europe/Moscow"));
        var str = formatter.format(instant);
        assertThat(str).isEqualTo("25.03.2007 14:15:30 GMT+4 MSD Europe/Moscow");
    }

    @Test
    void instantWithTimezoneRu() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(new Locale("ru"))
                .withZone(ZoneId.of("Europe/Moscow"));
        var str = formatter.format(instant);
        assertThat(str).isEqualTo("25 марта 2007 г., 14:15:30 MSD");
    }

}
