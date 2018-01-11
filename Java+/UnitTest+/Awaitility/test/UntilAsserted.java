import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class UntilAsserted {

    @Test
    public void untilAsserted() {
        final AtomicInteger n = new AtomicInteger(0);

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
