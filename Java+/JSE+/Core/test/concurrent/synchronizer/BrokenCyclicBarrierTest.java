package concurrent.synchronizer;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Прорыв барьера по таймауту.
 */
public class BrokenCyclicBarrierTest {
    private static final int height = 5;
    private static final CyclicBarrier barrier = new CyclicBarrier(height, () -> System.out.println("Barrier action"));

    @Test
    public void name() throws InterruptedException {
        broke();
        broke();
    }

    private void broke() throws InterruptedException {
        System.out.println("\nIs broken: " + barrier.isBroken());
        for (int i = 0; i < height - 1; i++) {
            new Worker().start();
        }
        System.out.println("Is broken: " + barrier.isBroken());
        Thread.sleep(1000);
        System.out.println("Is broken: " + barrier.isBroken());
        System.out.println("Exit");
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("Await: " + getId());
                barrier.await(500, TimeUnit.MILLISECONDS);
                System.out.println("Released: " + getId());
            } catch (InterruptedException e) {
                System.out.println("InterruptedException: " + getId());
            } catch (BrokenBarrierException e) {
                System.out.println("BrokenBarrierException: " + getId());
            } catch (TimeoutException e) {
                System.out.println("TimeoutException: " + getId());
            }
        }
    }
}
