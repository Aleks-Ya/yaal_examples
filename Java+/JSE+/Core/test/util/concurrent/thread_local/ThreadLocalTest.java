package util.concurrent.thread_local;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class ThreadLocalTest {

    @Test
    void test() throws InterruptedException {
        try (var se = Executors.newFixedThreadPool(2)) {
            se.submit(work);
            TimeUnit.SECONDS.sleep(2);
            se.submit(work);
        }
    }

    @Test
    void noInitValue() {
        var number = new ThreadLocal<Integer>();
        var notInitializedNumber = number.get();
        assertThat(notInitializedNumber).isNull();
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