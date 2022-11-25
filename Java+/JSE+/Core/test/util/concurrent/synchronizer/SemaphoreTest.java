package util.concurrent.synchronizer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

class SemaphoreTest {
    private static final int PERMITS = 2;
    private static final Semaphore SEMAPHORE = new Semaphore(PERMITS);

    @Test
    void acquire() throws InterruptedException {
        var threadNumber = PERMITS * 2;
        var workers = IntStream.range(0, threadNumber).boxed()
                .map(i -> new Worker("Worker-" + i))
                .peek(Thread::start).toList();
        Thread.sleep(10000);
        workers.forEach(Worker::interruptThread);
        Thread.sleep(5000);
        System.out.println("Exit");
    }

    @Test
    void releaseWithoutAcquire() {
        var semaphore = new Semaphore(5);
        semaphore.release();
        semaphore.release();
    }

    private static class Worker extends Thread {
        private boolean interruptedSignal;

        Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!interruptedSignal) {
                try {
                    System.out.printf("Acquiring: %s %s\n", getName(), getState());
                    SEMAPHORE.acquire();
                    System.out.printf("Acquired: %s %s\n", getName(), getState());
                    Thread.sleep(1000);
                    System.out.printf("Releasing: %s %s\n", getName(), getState());
                    SEMAPHORE.release();
                    System.out.printf("Released: %s %s\n", getName(), getState());
                } catch (InterruptedException e) {
                    System.out.printf("Interrupted: %s %s %s\n", getName(), getState(), isInterrupted());
                } finally {
                    System.out.printf("Finally: %s %s\n", getName(), getState());
                    SEMAPHORE.release();
                }
            }
            System.out.printf("While end: %s %s\n", getName(), getState());
        }

        void interruptThread() {
            interruptedSignal = true;
        }
    }
}
