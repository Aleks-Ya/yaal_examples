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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class InstantiateTest {
    @Test
    void local() {
        var localDate = LocalDate.now();
        var localTime = LocalTime.now();
        var localDateTime = LocalDateTime.now();
        var monthDay = MonthDay.now();
        var instant = Instant.now();
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

    @Test
    void moscowZoneId() {
        var msk1 = ZoneId.of("Europe/Moscow");
        var msk2 = ZoneId.of("UTC+03:00");

        var displayName = msk1.getDisplayName(TextStyle.FULL, new Locale("ru"));
        assertThat(displayName).isEqualTo("Москва");
    }

    @Test
    void epoch() {
        var epochMilli = Instant.now().toEpochMilli();
        var epochSecond = Instant.now().getEpochSecond();
    }

    @Test
    void duration() {
        var duration1 = Duration.of(10, ChronoUnit.DAYS);
        var duration2 = Duration.ofNanos(10);
        var duration3 = Duration.ofSeconds(10);
        var duration4 = Duration.ofSeconds(10, 500);
        var duration5 = Duration.ofMinutes(10);
        var duration6 = Duration.ofHours(10);
        var duration7 = Duration.ofDays(10);
        var duration8 = Duration.parse("PT20.345S");
        var duration9 = Duration.between(Instant.now(), Instant.now());
        var duration10 = Duration.from(duration1);
        var duration11 = Duration.ZERO;
    }
}
