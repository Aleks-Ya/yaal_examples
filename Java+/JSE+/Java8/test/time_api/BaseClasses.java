package time_api;

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
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        MonthDay monthDay = MonthDay.now();
        Instant instant = Instant.now();
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

    @Test
    public void moscowZoneId() {
        ZoneId msk1 = ZoneId.of("Europe/Moscow");
        ZoneId msk2 = ZoneId.of("UTC+03:00");

        String displayName = msk1.getDisplayName(TextStyle.FULL, new Locale("ru"));
        assertThat(displayName, equalTo("Moscow Time"));
    }
}
