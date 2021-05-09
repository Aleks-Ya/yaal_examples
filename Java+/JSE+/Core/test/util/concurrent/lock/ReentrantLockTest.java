package util.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    /**
     * Get lock twice.
     */
    @Test
    public void reenter() throws ExecutionException, InterruptedException {
        var log = new StringBuffer();
        var lock = new ReentrantLock();
        Runnable r1 = () -> {
            lock.lock();
            log.append("Locked by t1\n");
            lock.lock();
            log.append("Locked by t1 again\n");
            lock.unlock();
            log.append("Unlocked by t1\n");
            lock.unlock();
            log.append("Unlocked by t1 again\n");

        };
        Runnable r2 = () -> {
            lock.lock();
            log.append("Locked by t2\n");
            lock.lock();
            log.append("Locked by t2 again\n");
            lock.unlock();
            log.append("Unlocked by t2\n");
            lock.unlock();
            log.append("Unlocked by t2 again\n");
        };
        var executor = Executors.newFixedThreadPool(2);
        var f1 = executor.submit(r1);
        var f2 = executor.submit(r2);
        f1.get();
        f2.get();
        System.out.println(log);
    }
}