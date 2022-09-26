package java8.time.clock;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class ControlSystemTimeInTestsTest {
    @Test
    void control() {
        var now1 = Instant.parse("2007-03-25T10:15:30.00Z");
        var clock = Clock.fixed(now1, ZoneId.systemDefault());
        assertThat(Instant.now(clock)).isEqualTo(now1);
        assertThat(Instant.now()).isNotEqualTo(now1);

        var now2 = Instant.parse("2008-03-25T10:15:30.00Z");
        clock = Clock.fixed(now2, ZoneId.systemDefault());
        assertThat(Instant.now(clock)).isEqualTo(now2);
        assertThat(Instant.now()).isNotEqualTo(now1);
        assertThat(Instant.now()).isNotEqualTo(now2);
    }

}