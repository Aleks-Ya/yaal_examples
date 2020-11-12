package java8.time_api;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Форматирование дат.
 */
public class Format {
    @Test
    public void dateTime() {
        LocalDateTime dateTime = LocalDateTime.parse("2015-03-25T10:15:30");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = dateTime.format(formatter);
        assertEquals("25.03.2015", str);
    }

    @Test
    public void zonedDateTime() {
        ZonedDateTime dateTime = ZonedDateTime.of(2015, 3, 25, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = dateTime.format(formatter);
        assertEquals("25.03.2015", str);
    }

    @Test
    public void instant() {
        Instant instant = Instant.parse("2007-03-25T10:15:30.00Z");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        String str = formatter.format(instant);
        assertEquals("25.03.2007 14:15:30", str);
    }

    @Test
    public void instantWithTimezone() {
        Instant instant = Instant.parse("2007-03-25T10:15:30.00Z");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss O z VV").withZone(ZoneId.systemDefault());
        String str = formatter.format(instant);
        assertEquals("25.03.2007 14:15:30 GMT+4 MSD Europe/Moscow", str);
    }

    @Test
    public void instantWithTimezoneRu() {
        Instant instant = Instant.parse("2007-03-25T10:15:30.00Z");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(new Locale("ru"))
                .withZone(ZoneId.systemDefault());
        String str = formatter.format(instant);
        assertEquals("25 марта 2007 г. 14:15:30 MSD", str);
    }

}
