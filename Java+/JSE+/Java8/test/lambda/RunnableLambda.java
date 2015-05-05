package lambda;

import org.junit.Test;

/**
 * Использование лямбды для реализации интерфейса Runnable.
 */
public class RunnableLambda {

    @Test
    public void runnable() {
        Runnable r = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Runnable lambda: " + threadName);
        };
        new Thread(r).start();
    }
}
