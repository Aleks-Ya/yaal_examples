package util.concurrent.interrupt_thread;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Interrupt a Thread using {@link Thread#interrupt()} method.
 */
class InterruptThreadTest {
    private volatile boolean threadInterrupted = false;

    @Test
    void interrupt() {
        assertThat(threadInterrupted).isFalse();
        var t = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                threadInterrupted = true;
            }
        });
        t.start();
        assertThat(t.isInterrupted()).isFalse();
        t.interrupt();
        await().until(() -> threadInterrupted);
        await().until(t::isInterrupted);
    }
}