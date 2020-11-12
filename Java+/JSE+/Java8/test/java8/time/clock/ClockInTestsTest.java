package java8.time.clock;

import org.junit.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClockInTestsTest {
    @Test
    public void isHourElapsed() {
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        Clocks.instance.setClock(fixedClock);
        TimeElapse out = new TimeElapse();
        assertFalse(out.isHourElapsed());

        Clock clock30Minutes = Clock.offset(fixedClock, Duration.ofMinutes(30));
        Clocks.instance.setClock(clock30Minutes);
        assertFalse(out.isHourElapsed());

        Clock clock61Minutes = Clock.offset(fixedClock, Duration.ofMinutes(61));
        Clocks.instance.setClock(clock61Minutes);
        assertTrue(out.isHourElapsed());
    }

}