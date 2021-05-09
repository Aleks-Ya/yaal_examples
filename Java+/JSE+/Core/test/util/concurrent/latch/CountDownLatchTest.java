package util.concurrent.latch;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

class CountDownLatchTest {
    @Test
    void latch() throws InterruptedException, ExecutionException {
        var log = new StringBuffer();
        var latch = new CountDownLatch(2);
        Callable<Void> t1 = () -> {
            log.append("t1 is waiting for t2 and t3\n");
            latch.await();
            log.append("t1 finished waiting\n");
            return null;
        };
        Callable<Void> t2 = () -> {
            log.append("t2 is ready to count down the latch\n");
            latch.countDown();
            log.append("t2 has counted down the latch\n");
            return null;
        };
        Callable<Void> t3 = () -> {
            log.append("t3 is ready to count down the latch\n");
            latch.countDown();
            log.append("t3 has counted down the latch\n");
            return null;
        };
        var executor = Executors.newFixedThreadPool(3);
        var f1 = executor.submit(t1);
        var f2 = executor.submit(t2);
        var f3 = executor.submit(t3);
        f1.get();
        f2.get();
        f3.get();
        System.out.println(log);
    }
}
