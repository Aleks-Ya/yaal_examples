package java8.time;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Преобразование одних классов даты/времени в другие.
 */
public class Convert {

    @Test
    public void localDateToLocalDateTime() {
        var date = LocalDate.parse("2015-03-25");
        var time = LocalTime.MIDNIGHT;
        var dateTime = LocalDateTime.of(date, time);
        assertThat(dateTime.toString(), equalTo("2015-03-25T00:00"));
    }

    @Test
    public void localDateTimeToLocalDate() {
        var dateTime = LocalDateTime.parse("2015-03-25T10:15:30");
        var localDate = LocalDate.ofEpochDay(dateTime.getLong(ChronoField.EPOCH_DAY));
        assertThat(localDate.toString(), equalTo("2015-03-25"));
    }

    @Test
    public void localDateTimeToInstant() {
        var dateTime = LocalDateTime.parse("2015-03-25T10:15:30");
        var instant = dateTime.toInstant(ZoneOffset.UTC);
        assertThat(instant.toString(), equalTo("2015-03-25T10:15:30Z"));
    }

    @Test
    public void localDateToInstant() {
        var date = LocalDate.parse("2015-03-25");
        var instant = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        assertThat(instant.toString(), equalTo("2015-03-25T00:00:00Z"));
    }

    @Test
    public void instantToLocalDateTime() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Moscow"));
        assertThat(dateTime.toString(), equalTo("2007-03-25T14:15:30"));
    }

    @Test
    public void instantToLocalDate() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Moscow"));
        var date = LocalDate.ofEpochDay(dateTime.getLong(ChronoField.EPOCH_DAY));
        assertThat(date.toString(), equalTo("2007-03-25"));
    }

    @Test
    public void yearMonthToInstant() {
        var yearMonth = YearMonth.parse("2007-03");
        var instant = yearMonth.atEndOfMonth().atStartOfDay().toInstant(ZoneOffset.UTC);
        assertThat(instant.toString(), equalTo("2007-03-31T00:00:00Z"));
    }

    @Test
    public void dateToLocalDate() {
        var date = Date.from(Instant.parse("2007-03-25T10:15:30.00Z"));
        var localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertThat(localDate.toString(), equalTo("2007-03-25"));
    }

    @Test
    public void instantToZonedDateTime() {
        var instant = Instant.parse("2007-03-25T10:15:30.00Z");
        var dateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("Europe/Moscow"));
        assertThat(dateTime.toString(), equalTo("2007-03-25T14:15:30+04:00[Europe/Moscow]"));
    }

    @Test
    public void zonedDateTimeToInstant() {
        var date = ZonedDateTime.of(2007, 3, 25, 10, 15, 30, 5, ZoneId.of("+01:00"));
        var instant = date.toInstant();
        assertThat(instant.toString(), equalTo("2007-03-25T09:15:30.000000005Z"));
    }
}
