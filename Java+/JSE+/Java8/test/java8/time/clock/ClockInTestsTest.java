package java8.time.clock;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClockInTestsTest {
    @Test
    void isHourElapsed() {
        var fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        Clocks.instance.setClock(fixedClock);
        var out = new TimeElapse();
        assertFalse(out.isHourElapsed());

        var clock30Minutes = Clock.offset(fixedClock, Duration.ofMinutes(30));
        Clocks.instance.setClock(clock30Minutes);
        assertFalse(out.isHourElapsed());

        var clock61Minutes = Clock.offset(fixedClock, Duration.ofMinutes(61));
        Clocks.instance.setClock(clock61Minutes);
        assertTrue(out.isHourElapsed());
    }

}