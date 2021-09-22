package util.collection.queue.blocking;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Пример из https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html
 */
@Disabled
@SuppressWarnings("InfiniteLoopStatement")
class BlockingQueueJavadocExample {

    @Test
    void main() {
        BlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        var p = new Producer(q);
        var c1 = new Consumer(q);
        var c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }

    private static class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;

        Producer(BlockingQueue<Integer> q) {
            queue = q;
        }

        public void run() {
            try {
                while (true) {
                    queue.put(produce());
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException();
            }
        }

        private int n;

        Integer produce() {
            return ++n;
        }
    }

    private static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;

        Consumer(BlockingQueue<Integer> q) {
            queue = q;
        }

        public void run() {
            try {
                while (true) {
                    consume(queue.take());
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException();
            }
        }

        void consume(Object x) {
            System.out.println("n=" + x);
        }
    }

}
