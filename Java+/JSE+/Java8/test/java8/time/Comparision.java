package java8.time;

import org.junit.Test;

import java.time.*;

/**
 * Сравнение дат.
 */
public class Comparision {
    @Test
    public void inInterval() {
        LocalDate since = LocalDate.of(2012, 4, 25);
        LocalDate till = LocalDate.of(2020, 1, 18);

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
