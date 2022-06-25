package java8.time;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Сравнение дат.
 */
public class ComparisionTest {
    @Test
    void inInterval() {
        var since = LocalDate.of(2012, 4, 25);
        var till = LocalDate.of(2020, 1, 18);

        var localDate = LocalDate.now();
        var localTime = LocalTime.now();
        var localDateTime = LocalDateTime.now();
        var monthDay = MonthDay.now();
        var instant = Instant.now();
    }

    @Test
    void intervals() {
        var duration = Duration.between(LocalTime.now(), LocalTime.now().plusMinutes(1));
        var period = Period.between(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    @Test
    void timeZone() {
        var clock = Clock.fixed(Instant.now(), ZoneId.of("UTC+4"));

        var zonedDateTime = ZonedDateTime.now();
        var zoneId = ZoneId.systemDefault();
        var zoneOffset = ZoneOffset.ofHours(3);

        var offsetDateTime = OffsetDateTime.now();
        var offsetTime = OffsetTime.now();
    }
}
