package failsafe.timeout;

import dev.failsafe.Failsafe;
import dev.failsafe.Timeout;
import dev.failsafe.TimeoutExceededException;
import failsafe.LongTimeCallable;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeoutTest {
    @Test
    void timeout() {
        var timeout = Timeout.builder(Duration.ofMillis(200)).withInterrupt().build();
        Callable<String> callable = new LongTimeCallable<>("abc", Duration.ofMillis(500));
        assertThatThrownBy(() -> Failsafe.with(timeout).get(callable::call))
                .isInstanceOf(TimeoutExceededException.class);
    }
}