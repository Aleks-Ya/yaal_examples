package concurrent;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Запуск потоков по расписанию.
 */
public class ScheduledExecutorServiceUse {

    /**
     * Запуск задачи отложен на определенное время.
     */
    @Test
    public void delay() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int delay = 2;
        executor.schedule((Runnable) () -> System.out.println("delay"), delay, TimeUnit.SECONDS);
        Thread.sleep(10_000);
    }

    /**
     * Переодический запуск задачи.
     */
    @Test
    public void period() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int period = 2;
        int initialDelay = 0;
        executor.scheduleAtFixedRate((Runnable) () -> System.out.println("period"), initialDelay, period, TimeUnit.SECONDS);
        Thread.sleep(10_000);
    }
}
