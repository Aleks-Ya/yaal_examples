package java8.time;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Парсинг строк с датами и временем.
 */
class ParsingTest {
    @Test
    void date() {
        assertThat(LocalDate.parse("2015-03-25")).isNotNull();
    }

    @Test
    void dateTime() {
        //Without formatter
        assertThat(LocalDateTime.parse("2015-03-25T10:15:30")).isNotNull();

        //With formatter
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        assertThat(LocalDateTime.parse("25.03.2015 10:40:50", formatter)).isNotNull();

        assertThat(LocalDateTime.parse("2022-08-15T03:22:32.19838")).isNotNull();
    }

    @Test
    void instant() {
        assertThat(Instant.parse("2007-03-25T10:15:30.00Z")).isNotNull();
    }

    @Test
    void yearMonth() {
        assertThat(YearMonth.parse("2007-03")).isNotNull();
    }

    @Test
    void zonedDateTime() {
        var exp1 = ZonedDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));
        assertThat(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]")).isEqualTo(exp1);

        var exp2 = ZonedDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneId.of("+01:00"));
        assertThat(ZonedDateTime.parse("2007-12-03T10:15:30+01:00")).isEqualTo(exp2);

        ZonedDateTime.parse("2016-01-01T14:01:00Z");
    }

    /**
     * Parse date of "2019-01-30 15:31:05.234778" format created by "datetime.datetime.utcnow()" in Python.
     * ".234778" means microseconds.
     */
    @Test
    void pythonUtcNow() {
        var date = "2019-01-30 15:31:05.234778";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(date, formatter);
        assertThat(localDateTime.get(ChronoField.MICRO_OF_SECOND)).isEqualTo(234778);
        assertThat(localDateTime.toString()).isEqualTo("2019-01-30T15:31:05.234778");
    }

    @Test
    void pythonUtcNowWithBuilder() {
        var date = "2019-01-30 15:31:05.234778";
        var formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .appendFraction(ChronoField.MICRO_OF_SECOND, 6, 6, true)
                .toFormatter();
        var localDateTime = LocalDateTime.parse(date, formatter);
        assertThat(localDateTime.get(ChronoField.MICRO_OF_SECOND)).isEqualTo(234778);
        assertThat(localDateTime.toString()).isEqualTo("2019-01-30T15:31:05.234778");
    }

    @Test
    void duration() {
        assertThat(Duration.parse("PT20.345S")).isEqualTo(Duration.ofSeconds(20, 345000000));
        assertThat(Duration.parse("P2DT3H4M")).isEqualTo(Duration.ofDays(2).plusHours(3).plusMinutes(4));
    }
}
