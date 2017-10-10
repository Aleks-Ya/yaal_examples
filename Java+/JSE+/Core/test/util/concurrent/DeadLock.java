package util.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Демонстрация взаимной блокировки.
 */
public class DeadLock {

    private static class Work implements Runnable {
        private final Object a;
        private final Object b;

        Work(Object a, Object b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + " took " + a + " and wait " + b);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b) {
                    throw new IllegalStateException("Unreachable");
                }
            }
        }
    }


    @Test
    public void test() throws InterruptedException {
        ExecutorService se = Executors.newFixedThreadPool(2);
        final Object resource1 = new Object();
        final Object resource2 = new Object();
        se.submit(new Work(resource1, resource2));
        se.submit(new Work(resource2, resource1));
        se.awaitTermination(1, TimeUnit.HOURS);
    }
}