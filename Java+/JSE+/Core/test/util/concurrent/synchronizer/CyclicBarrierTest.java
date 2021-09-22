package util.concurrent.synchronizer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Барьер отпускает потоки, когда их количество равно высоте барьера.
 */
class CyclicBarrierTest {
    private static final int height = 3;
    private static final CyclicBarrier barrier = new CyclicBarrier(height, () -> System.out.println("Barrier action"));

    @Test
    void name() throws InterruptedException {
        for (int i = 0; i < height; i++) {
            new Worker().start();
        }
        Thread.sleep(1000);
        System.out.println("Exit");
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("Await: " + getId());
                barrier.await();
                System.out.println("Released: " + getId());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
