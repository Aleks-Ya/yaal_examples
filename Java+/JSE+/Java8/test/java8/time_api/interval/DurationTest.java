package java8.time_api.interval;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DurationTest {

    @Test
    public void instantiate() {
        Duration d1 = Duration.ofSeconds(10);
        Duration d2 = Duration.of(1, ChronoUnit.HOURS);
        Duration d3 = Duration.between(LocalTime.now(), LocalTime.now().plusMinutes(1));
    }

    @Test
    public void format() {
        Duration d = Duration.ofSeconds(93784);

        long days = d.toDays();
        Duration noDays = d.minusDays(days);

        long hours = noDays.toHours();
        Duration noHours = noDays.minusHours(hours);

        long minutes = noHours.toMinutes();
        Duration noMinutes = noHours.minusMinutes(minutes);

        long seconds = noMinutes.getSeconds();

        assertThat(days, equalTo(1L));
        assertThat(hours, equalTo(2L));
        assertThat(minutes, equalTo(3L));
        assertThat(seconds, equalTo(4L));
    }


}
