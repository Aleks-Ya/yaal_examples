import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UntilAsserted {

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
}