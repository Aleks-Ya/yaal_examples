package util.concurrent.thread_local;

import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    private static Runnable work = new Runnable() {
        private ThreadLocal<LocalTime> date = ThreadLocal.withInitial(() -> {
            LocalTime initValue = LocalTime.of(12, 12);
            System.out.printf("Init %s with %s\n", Thread.currentThread().getName(), initValue);
            return initValue;
        });

        @Override
        public void run() {
            System.out.printf("Thread name=%s, init value=%s\n", Thread.currentThread().getName(), date.get());
            date.set(LocalTime.now());
            System.out.printf("Thread name=%s, value=%s\n", Thread.currentThread().getName(), date.get());
        }
    };

    @Test
    public void test() throws InterruptedException {
        ExecutorService se = Executors.newFixedThreadPool(2);
        se.submit(work);
        TimeUnit.SECONDS.sleep(2);
        se.submit(work);
        se.shutdown();
    }

}