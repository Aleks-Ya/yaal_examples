package java8.time;

import org.junit.Test;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BaseClasses {
    @Test
    public void local() {
        var localDate = LocalDate.now();
        var localTime = LocalTime.now();
        var localDateTime = LocalDateTime.now();
        var monthDay = MonthDay.now();
        var instant = Instant.now();
    }

    @Test
    public void timeZone() {
        var clock = Clock.fixed(Instant.now(), ZoneId.of("UTC+4"));

        var zonedDateTime = ZonedDateTime.now();
        var zoneId = ZoneId.systemDefault();
        var zoneOffset = ZoneOffset.ofHours(3);

        var offsetDateTime = OffsetDateTime.now();
        var offsetTime = OffsetTime.now();
    }

    @Test
    public void moscowZoneId() {
        var msk1 = ZoneId.of("Europe/Moscow");
        var msk2 = ZoneId.of("UTC+03:00");

        var displayName = msk1.getDisplayName(TextStyle.FULL, new Locale("ru"));
        assertThat(displayName, equalTo("Москва"));
    }
}
