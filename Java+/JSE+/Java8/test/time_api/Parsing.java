package time_api;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Парсинг строк с датами и временем.
 *
 * @author yablokov a.
 */
public class Parsing {
    @Test
    public void date() {
        LocalDate date = LocalDate.parse("2015-03-25");
    }

    @Test
    public void dateTime() {
        //Without formatter
        LocalDateTime dateTime = LocalDateTime.parse("2015-03-25T10:15:30");

        //With formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime dateTime3 = LocalDateTime.parse("25.03.2015 10:40:50", formatter);
    }

    @Test
    public void instant() {
        Instant instant = Instant.parse("2007-03-25T10:15:30.00Z");
    }
}
