package time_api;

import org.junit.Test;

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

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Преобразование одних классов даты/времени в другие.
 */
public class Convert {

    @Test
    public void localDateToLocalDateTime() {
        LocalDate date = LocalDate.parse("2015-03-25");
        LocalTime time = LocalTime.MIDNIGHT;
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        assertThat(dateTime.toString(), equalTo("2015-03-25T00:00"));
    }

    @Test
    public void localDateTimeToLocalDate() {
        LocalDateTime dateTime = LocalDateTime.parse("2015-03-25T10:15:30");
        LocalDate localDate = LocalDate.ofEpochDay(dateTime.getLong(ChronoField.EPOCH_DAY));
        assertThat(localDate.toString(), equalTo("2015-03-25"));
    }

    @Test
    public void localDateTimeToInstant() {
        LocalDateTime dateTime = LocalDateTime.parse("2015-03-25T10:15:30");
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);
        assertThat(instant.toString(), equalTo("2015-03-25T10:15:30Z"));
    }

    @Test
    public void localDateToInstant() {
        LocalDate date = LocalDate.parse("2015-03-25");
        Instant instant = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        assertThat(instant.toString(), equalTo("2015-03-25T00:00:00Z"));
    }

    @Test
    public void instantToLocalDateTime() {
        Instant instant = Instant.parse("2007-03-25T10:15:30.00Z");
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        assertThat(dateTime.toString(), equalTo("2007-03-25T14:15:30"));
    }

    @Test
    public void instantToLocalDate() {
        Instant instant = Instant.parse("2007-03-25T10:15:30.00Z");
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate date = LocalDate.ofEpochDay(dateTime.getLong(ChronoField.EPOCH_DAY));
        assertThat(date.toString(), equalTo("2007-03-25"));
    }

    @Test
    public void yearMonthToInstant() {
        YearMonth yearMonth = YearMonth.parse("2007-03");
        Instant instant = yearMonth.atEndOfMonth().atStartOfDay().toInstant(ZoneOffset.UTC);
        assertThat(instant.toString(), equalTo("2007-03-31T00:00:00Z"));
    }

    @Test
    public void dateToLocalDate() {
        Date date = Date.from(Instant.parse("2007-03-25T10:15:30.00Z"));
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertThat(localDate.toString(), equalTo("2007-03-25"));
    }

    @Test
    public void zonedDateTimeToInstant() {
        ZonedDateTime date = ZonedDateTime.of(2007, 3, 25, 10, 15, 30, 5, ZoneId.of("+01:00"));
        Instant instant = date.toInstant();
        assertThat(instant.toString(), equalTo("2007-03-25T09:15:30.000000005Z"));
    }
}
