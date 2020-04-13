package time_api;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Парсинг строк с датами и временем.
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

    @Test
    public void zonedDateTime() {
        ZonedDateTime exp1 = ZonedDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));
        assertThat(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"), equalTo(exp1));

        ZonedDateTime exp2 = ZonedDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneId.of("+01:00"));
        assertThat(ZonedDateTime.parse("2007-12-03T10:15:30+01:00"), equalTo(exp2));

        ZonedDateTime.parse("2016-01-01T14:01:00Z");
    }

    /**
     * Parse date of "2019-01-30 15:31:05.234778" format created by "datetime.datetime.utcnow()" in Python.
     * ".234778" means microseconds.
     */
    @Test
    public void pythonUtcNow() {
        String date = "2019-01-30 15:31:05.234778";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        assertThat(localDateTime.get(ChronoField.MICRO_OF_SECOND), equalTo(234778));
        assertThat(localDateTime.toString(), equalTo("2019-01-30T15:31:05.234778"));
    }

    @Test
    public void pythonUtcNowWithBuilder() {
        String date = "2019-01-30 15:31:05.234778";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .appendFraction(ChronoField.MICRO_OF_SECOND, 6, 6, true)
                .toFormatter();
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        assertThat(localDateTime.get(ChronoField.MICRO_OF_SECOND), equalTo(234778));
        assertThat(localDateTime.toString(), equalTo("2019-01-30T15:31:05.234778"));
    }
}
