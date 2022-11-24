package scheduling.scheduled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Scheduled method throws an exception.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ScheduledExceptionTest.Config.class)
class ScheduledExceptionTest {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final CopyOnWriteArrayList<String> THREADS = new CopyOnWriteArrayList<>();
    private static final long SLEEP = 5000;
    private static final long DELAY = 500;


    @Test
    void test() throws InterruptedException {
        Thread.sleep(SLEEP);
        var counter = COUNTER.get();
        assertThat(counter).isEqualTo(SLEEP / DELAY);
        var t1 = THREADS.get(0);
        assertThat(THREADS).hasSize(counter).allSatisfy(thread -> assertThat(thread).isEqualTo(t1));
    }

    @Configuration
    @EnableScheduling
    static class Config {
        @Scheduled(fixedDelay = DELAY)
        void exception() {
            System.out.println("Throwing exception in " + Thread.currentThread().getName());
            COUNTER.incrementAndGet();
            THREADS.add(Thread.currentThread().getName());
            throw new RuntimeException("abc");
        }
    }
}
