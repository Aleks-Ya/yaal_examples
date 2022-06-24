package java8.lambda;

import org.junit.jupiter.api.Test;

/**
 * Использование лямбды для реализации интерфейса Runnable.
 */
public class RunnableLambda {

    @Test
    void runnable() {
        Runnable r = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Runnable lambda: " + threadName);
        };
        new Thread(r).start();
    }
}
