package time_api;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 * Форматирование дат.
 *
 * @author yablokov a.
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
}
