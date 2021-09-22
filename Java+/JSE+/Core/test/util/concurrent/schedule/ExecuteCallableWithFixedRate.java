package util.concurrent.schedule;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Запуск Callable периодически.
 */
class ExecuteCallableWithFixedRate {

    /**
     * Переодический запуск задачи.
     */
    @Test
    void period() throws InterruptedException, ExecutionException {
        String text = "aaa";
        Callable<String> callable = () -> text;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture<String> future = executor.schedule(callable, 0, TimeUnit.SECONDS);
        assertThat(future.get(), equalTo(text));


        int period = 2;
        int initialDelay = 0;
//        executor.scheduleAtFixedRate(callable, initialDelay, period, TimeUnit.SECONDS);
        Thread.sleep(10_000);
    }
}
