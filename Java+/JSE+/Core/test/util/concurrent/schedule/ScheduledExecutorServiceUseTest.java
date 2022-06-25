package util.concurrent.schedule;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Запуск потоков по расписанию.
 */
class ScheduledExecutorServiceUseTest {

    /**
     * Запуск задачи отложен на определенное время.
     */
    @Test
    void delay() throws InterruptedException {
        var executor = Executors.newScheduledThreadPool(1);
        var delay = 2;
        executor.schedule((Runnable) () -> System.out.println("delay"), delay, TimeUnit.SECONDS);
        Thread.sleep(10_000);
    }

    /**
     * Переодический запуск задачи.
     */
    @Test
    void period() throws InterruptedException {
        var executor = Executors.newScheduledThreadPool(1);
        var period = 2;
        var initialDelay = 0;
        executor.scheduleAtFixedRate(() -> System.out.println("period"), initialDelay, period, TimeUnit.SECONDS);
        Thread.sleep(10_000);
    }
}
