package util.concurrent.latch;

import org.junit.Test;

import java.util.concurrent.*;

public class CountDownLatchTest {
    @Test
    public void latch() throws InterruptedException, ExecutionException {
        CountDownLatch latch = new CountDownLatch(1);
        Callable<Void> t1 = () -> {
            System.out.println("t1 is waiting for t2");
            latch.await();
            System.out.println("t1 finished waiting");
            return null;
        };
        Callable<Void> t2 = () -> {
            System.out.println("t2 is ready to count down the latch");
            latch.countDown();
            System.out.println("t2 has counted down the latch");
            return null;
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Void> f1 = executor.submit(t1);
        Future<Void> f2 = executor.submit(t2);
        f1.get();
        f2.get();
    }
}
