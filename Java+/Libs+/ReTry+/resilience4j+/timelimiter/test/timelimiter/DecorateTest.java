package timelimiter;

import io.github.resilience4j.timelimiter.TimeLimiter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DecorateTest {
    @Test
    void decorateFutureSupplier() {
        var timeLimiter = TimeLimiter.of(ofMillis(200));
        var cf = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var supplier = timeLimiter.decorateFutureSupplier(() -> cf);
        assertThatThrownBy(supplier::call).isInstanceOf(TimeoutException.class);
    }
}