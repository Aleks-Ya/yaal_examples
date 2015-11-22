package time_api;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;

/**
 * Парсинг строк с датами и временем.
 *
 * @author yablokov a.
 */
public class Parsing {
    @Test
    public void date() {
        assertNotNull(LocalDate.parse("2015-03-25"));
    }

    @Test
    public void dateTime() {
        //Without formatter
        assertNotNull(LocalDateTime.parse("2015-03-25T10:15:30"));

        //With formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        assertNotNull(LocalDateTime.parse("25.03.2015 10:40:50", formatter));
    }

    @Test
    public void instant() {
        assertNotNull(Instant.parse("2007-03-25T10:15:30.00Z"));
    }

    @Test
    public void yearMonth() {
        assertNotNull(YearMonth.parse("2007-03"));
    }
}
