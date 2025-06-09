package lang;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DurationFormatUtilsTest {

    @Test
    void formatDuration() {
        var millis = Duration.ofSeconds(93784).toMillis();
        assertThat(DurationFormatUtils.formatDurationHMS(millis)).isEqualTo("26:03:04.000");
        assertThat(DurationFormatUtils.formatDurationISO(millis)).isEqualTo("P0Y0M1DT2H3M4.000S");
        assertThat(DurationFormatUtils.formatDuration(millis, "HH:mm:ss")).isEqualTo("26:03:04");
    }

    @Test
    void formatPeriod() {
        var startMillis = Instant.parse("2020-01-01T00:00:00Z").toEpochMilli();
        var endMillis = Instant.parse("2020-01-03T01:30:40Z").toEpochMilli();
        assertThat(DurationFormatUtils.formatPeriod(startMillis, endMillis, "HH:mm:ss")).isEqualTo("49:30:40");
    }
}
