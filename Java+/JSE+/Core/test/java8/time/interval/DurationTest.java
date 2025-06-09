package java8.time.interval;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class DurationTest {

    @Test
    void instantiate() {
        var d1 = Duration.ofSeconds(10);
        var d2 = Duration.of(1, ChronoUnit.HOURS);
        var d3 = Duration.between(LocalTime.now(), LocalTime.now().plusMinutes(1));
    }

    @Test
    void format() {
        var d = Duration.ofSeconds(93784);

        var days = d.toDays();
        var noDays = d.minusDays(days);

        var hours = noDays.toHours();
        var noHours = noDays.minusHours(hours);

        var minutes = noHours.toMinutes();
        var noMinutes = noHours.minusMinutes(minutes);

        var seconds = noMinutes.getSeconds();

        assertThat(days).isEqualTo(1L);
        assertThat(hours).isEqualTo(2L);
        assertThat(minutes).isEqualTo(3L);
        assertThat(seconds).isEqualTo(4L);
    }

    @Test
    void compare() {
        var small = Duration.ofSeconds(10);
        var big = Duration.ofHours(1);
        assertThat(small.compareTo(big)).isEqualTo(-1);
        assertThat(big.compareTo(small)).isEqualTo(1);
        var isSmallLess = small.compareTo(big) < 0;
        assertThat(isSmallLess).isTrue();
    }

    @Test
    void toStr() {
        var d = Duration.ofSeconds(93784);
        assertThat(d).hasToString("PT26H3M4S");
    }
}
