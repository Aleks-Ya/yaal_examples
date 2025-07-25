import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UntilAssertedTest {

    @Test
    void untilAssertedAtMost() {
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
                .untilAsserted(() -> assertEquals(1, n.get()));
    }

    @Test
    void untilAssertedAtLeast() {
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
                .untilAsserted(() -> assertEquals(1, n.get()));
    }

    @Test
    void during_success() {
        await().during(1, SECONDS).until(() -> true);
    }

    @Test
    void during_fail() {
        assertThatThrownBy(() -> await().during(1, SECONDS).until(() -> false))
                .isInstanceOf(ConditionTimeoutException.class);

    }
}
