package util.concurrent.wait_nofity;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Use methods {@link Object#wait()} and {@link Object#notify()}.
 */
class WaitNotifyTest {

    @Test
    void waitNotify() throws InterruptedException, ExecutionException {
        var lock = new Object();
        var se = Executors.newFixedThreadPool(2);
        var f1 = se.submit((Callable<Void>) () -> {
            System.out.printf("Thread %s started waiting for notify...\n", Thread.currentThread().getName());
            synchronized (lock) {
                lock.wait();
            }
            System.out.printf("Thread %s finished waiting.\n", Thread.currentThread().getName());
            return null;
        });
        var f2 = se.submit((Callable<Void>) () -> {
            var millis = 1000;
            System.out.printf("Thread %s started waiting %d millis...\n", Thread.currentThread().getName(), millis);
            Thread.sleep(millis);
            System.out.printf("Thread %s will notify the lock...\n", Thread.currentThread().getName());
            synchronized (lock) {
                lock.notifyAll();
            }
            System.out.printf("Thread %s notified the lock.\n", Thread.currentThread().getName());
            return null;
        });
        f1.get();
        f2.get();
        se.shutdown();
    }
}