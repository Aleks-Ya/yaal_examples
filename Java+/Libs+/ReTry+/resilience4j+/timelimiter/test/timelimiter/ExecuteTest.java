package timelimiter;

import io.github.resilience4j.timelimiter.TimeLimiter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExecuteTest {
    @Test
    void executeFutureSupplier() {
        var timeLimiter = TimeLimiter.of(ofMillis(200));
        var cf = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        assertThatThrownBy(() -> timeLimiter.executeFutureSupplier(() -> cf))
                .isInstanceOf(TimeoutException.class);
    }
}