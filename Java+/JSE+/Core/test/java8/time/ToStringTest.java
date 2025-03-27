package java8.time;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ToStringTest {
    @Test
    void localDate() {
        assertThat(LocalDate.of(2015, 3, 25)).hasToString("2015-03-25");
    }

    @Test
    void localTime() {
        assertThat(LocalTime.of(14, 50, 45)).hasToString("14:50:45");
        assertThat(LocalTime.of(14, 50, 45, 123456)).hasToString("14:50:45.000123456");
        assertThat(LocalTime.of(14, 50, 45, 123456).withNano(0))
                .hasToString("14:50:45");
    }

    @Test
    void localDateTime() {
        assertThat(LocalDateTime.of(2015, 3, 25, 14, 50))
                .hasToString("2015-03-25T14:50");
    }

    @Test
    void instant() {
        assertThat(Instant.parse("2007-03-25T10:15:30.00Z")).hasToString("2007-03-25T10:15:30Z");
    }

    @Test
    void date() {
        var date = Date.from(Instant.parse("2007-03-25T10:15:30.00Z"));
        assertThat(date).hasToString("Sun Mar 25 13:15:30 TRST 2007");
    }

    @Test
    void yearMonth() {
        assertThat(YearMonth.of(2015, 3)).hasToString("2015-03");
    }

    @Test
    void zonedDateTime() {
        assertThat(ZonedDateTime.of(2015, 3, 25, 14, 50, 45, 123456,
                ZoneId.of("Europe/London")))
                .hasToString("2015-03-25T14:50:45.000123456Z[Europe/London]");
    }
}
