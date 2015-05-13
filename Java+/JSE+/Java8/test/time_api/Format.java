package time_api;

import org.junit.Test;

import java.time.LocalDateTime;
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
}
