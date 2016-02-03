package time_api;

import org.junit.Test;

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

public class BaseClasses {
    @Test
    public void local() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        MonthDay monthDay = MonthDay.now();
        Instant instant = Instant.now();
    }

    @Test
    public void intervals() {
        Duration duration = Duration.between(LocalTime.now(), LocalTime.now().plusMinutes(1));
        Period period = Period.between(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    @Test
    public void timeZone() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.of("UTC+4"));

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset zoneOffset = ZoneOffset.ofHours(3);

        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        OffsetTime offsetTime = OffsetTime.now();
    }
}
