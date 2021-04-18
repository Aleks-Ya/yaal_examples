package java8.time;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Форматирование дат.
 */
public class Format {
    @Test
    public void dateTime() {
        var dateTime = LocalDateTime.parse("2015-03-25T10:15:30");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var str = dateTime.format(formatter);
        assertEquals("25.03.2015", str);
    }

    @Test
    public void dateTimeDefault() {
        var str = "2015-03-25T10:15:30";
        var dateTime = LocalDateTime.parse(str);
        assertThat(dateTime.toString(), equalTo(str));
    }

    @Test
    public void zonedDateTime() {
        var dateTime = ZonedDateTime.of(2015, 3, 25, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var str = dateTime.format(formatter);
        assertEquals("25.03.2015", str);
    }

    @Test
    public void instant() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(ZoneId.of("Europe/Moscow"));
        var str = formatter.format(instant);
        assertEquals("25.03.2007 14:15:30", str);
    }

    @Test
    public void instantWithTimezone() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss O z VV").withZone(ZoneId.of("Europe/Moscow"));
        var str = formatter.format(instant);
        assertEquals("25.03.2007 14:15:30 GMT+4 MSD Europe/Moscow", str);
    }

    @Test
    public void instantWithTimezoneRu() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(new Locale("ru"))
                .withZone(ZoneId.of("Europe/Moscow"));
        var str = formatter.format(instant);
        assertEquals("25 марта 2007 г., 14:15:30 MSD", str);
    }

}
