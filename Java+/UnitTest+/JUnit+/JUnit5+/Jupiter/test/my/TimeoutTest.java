package my;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class TimeoutTest {
    @Test
    @Timeout(1)
    void timeout() throws InterruptedException {
        Thread.sleep(2000);
    }
}
