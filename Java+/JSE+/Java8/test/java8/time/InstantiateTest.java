package java8.time;

import org.junit.jupiter.api.Test;

import java.time.Clock;
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
}
