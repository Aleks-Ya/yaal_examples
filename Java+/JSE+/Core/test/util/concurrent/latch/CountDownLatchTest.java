package util.concurrent.latch;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

class CountDownLatchTest {
    @Test
    void latch() {
        var log = new StringBuffer();
        var latch = new CountDownLatch(2);
        Runnable r1 = () -> {
            try {
                log.append("r1 is waiting for r2 and r3\n");
                latch.await();
                log.append("r1 finished waiting\n");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable r2 = () -> {
            log.append("r2 is ready to count down the latch\n");
            latch.countDown();
            log.append("r2 has counted down the latch\n");
        };
        Runnable r3 = () -> {
            log.append("r3 is ready to count down the latch\n");
            latch.countDown();
            log.append("r3 has counted down the latch\n");
        };
        var executor = Executors.newFixedThreadPool(3);
        executor.execute(r1);
        executor.execute(r2);
        executor.execute(r3);
        System.out.println(log);
    }
}
