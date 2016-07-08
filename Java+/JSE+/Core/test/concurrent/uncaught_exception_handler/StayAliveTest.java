package concurrent.uncaught_exception_handler;

import org.junit.Test;

/**
 * Позволяет ли UncaughtExceptionHandler оставить поток живым, если упало необработанное исключение?
 */
public class StayAliveTest {

    @Test
    public void name() throws InterruptedException {
        MyThread t = new MyThread();
        t.start();
        Thread.sleep(500);
        System.out.println(t.getState());
    }

    private static class MyThread extends Thread {
        private boolean throwException = true;

        MyThread() {
            setUncaughtExceptionHandler((t, e) -> {
                System.out.println(t + " " + e);
                System.out.println(t.getState());
                throwException = false;
            });
        }

        @Override
        public void run() {
            while (true) {
                if (throwException) {
                    throw new RuntimeException();
                }
            }
        }
    }
}
