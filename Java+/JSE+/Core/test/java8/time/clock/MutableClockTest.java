package java8.time.clock;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class MutableClockTest {
    @Test
    void setInstant() {
        var startInstant = Instant.parse("2007-12-03T10:15:30.00Z");
        var clock = MutableClock.mutable(startInstant);
        var timeService = new TimeService(clock);
        assertThat(timeService.timePastFromCreation()).isEqualTo(Duration.ZERO);

        var nowInstant = startInstant.plusSeconds(1);
        clock.setInstant(nowInstant);
        assertThat(timeService.timePastFromCreation()).isEqualTo(Duration.ofSeconds(1));
    }

    @Test
    void plusTemporalUnit() {
        var startInstant = Instant.parse("2007-12-03T10:15:30.00Z");
        var clock = MutableClock.mutable(startInstant);
        var timeService = new TimeService(clock);
        assertThat(timeService.timePastFromCreation()).isEqualTo(Duration.ZERO);

        clock.plus(2, ChronoUnit.MINUTES);
        assertThat(timeService.timePastFromCreation()).isEqualTo(Duration.ofMinutes(2));
    }

    @Test
    void plusDuration() {
        var startInstant = Instant.parse("2007-12-03T10:15:30.00Z");
        var clock = MutableClock.mutable(startInstant);
        var timeService = new TimeService(clock);
        assertThat(timeService.timePastFromCreation()).isEqualTo(Duration.ZERO);

        clock.plus(Duration.ofMinutes(5));
        assertThat(timeService.timePastFromCreation()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void create() {
        var clock = MutableClock.mutable();
        assertThat(clock).isNotNull();
    }
}