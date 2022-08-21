import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UntilAssertedTest {

    @Test
    void untilAsserted() {
        final var n = new AtomicInteger(0);

        new Thread(() -> {
            try {
                Thread.sleep(100);
                n.set(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        await()
                .atMost(500, MILLISECONDS)
                .pollInterval(10, MILLISECONDS)
                .untilAsserted(() -> assertEquals(n.get(), 1));
    }

    @Test
    void untilAssertedAtLest() {
        final var n = new AtomicInteger(0);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                n.set(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        await()
                .atLeast(2, SECONDS)
                .pollInterval(500, MILLISECONDS)
                .untilAsserted(() -> assertEquals(n.get(), 1));
    }
}
