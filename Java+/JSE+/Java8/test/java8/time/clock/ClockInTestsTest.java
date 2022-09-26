package java8.time.clock;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class ClockInTestsTest {
    @Test
    void isHourElapsed() {
        var fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        Clocks.instance.setClock(fixedClock);
        var out = new TimeElapse();
        assertThat(out.isHourElapsed()).isFalse();

        var clock30Minutes = Clock.offset(fixedClock, Duration.ofMinutes(30));
        Clocks.instance.setClock(clock30Minutes);
        assertThat(out.isHourElapsed()).isFalse();

        var clock61Minutes = Clock.offset(fixedClock, Duration.ofMinutes(61));
        Clocks.instance.setClock(clock61Minutes);
        assertThat(out.isHourElapsed()).isTrue();
    }

}