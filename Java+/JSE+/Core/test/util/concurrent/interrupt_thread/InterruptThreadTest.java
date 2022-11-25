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
    void interrupt() throws InterruptedException {
        assertThat(threadInterrupted).isFalse();
        var t = new Thread() {
            @Override
            public void run() {
                try {
                    assertThat(isInterrupted()).isFalse();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    threadInterrupted = true;
                    assertThat(isInterrupted()).isFalse();
                }
            }
        };
        t.start();
        Thread.sleep(500);
        assertThat(threadInterrupted).isFalse();
        assertThat(t.isInterrupted()).isFalse();
        t.interrupt();
        await().until(() -> threadInterrupted);
        assertThat(t.isInterrupted()).isFalse();
    }
}