package util.concurrent.thread_local;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ThreadLocalTest {

    @Test
    void test() throws InterruptedException {
        var se = Executors.newFixedThreadPool(2);
        se.submit(work);
        TimeUnit.SECONDS.sleep(2);
        se.submit(work);
        se.shutdown();
    }

    private static final Runnable work = new Runnable() {
        private final ThreadLocal<LocalTime> date = ThreadLocal.withInitial(() -> {
            var initValue = LocalTime.of(12, 12);
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

}