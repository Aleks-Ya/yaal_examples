package java8.time.interval;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DurationTest {

    @Test
    public void instantiate() {
        var d1 = Duration.ofSeconds(10);
        var d2 = Duration.of(1, ChronoUnit.HOURS);
        var d3 = Duration.between(LocalTime.now(), LocalTime.now().plusMinutes(1));
    }

    @Test
    public void format() {
        var d = Duration.ofSeconds(93784);

        var days = d.toDays();
        var noDays = d.minusDays(days);

        var hours = noDays.toHours();
        var noHours = noDays.minusHours(hours);

        var minutes = noHours.toMinutes();
        var noMinutes = noHours.minusMinutes(minutes);

        var seconds = noMinutes.getSeconds();

        assertThat(days, equalTo(1L));
        assertThat(hours, equalTo(2L));
        assertThat(minutes, equalTo(3L));
        assertThat(seconds, equalTo(4L));
    }


}
