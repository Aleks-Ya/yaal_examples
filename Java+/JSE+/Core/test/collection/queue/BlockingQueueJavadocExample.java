package collection.queue;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Пример из https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html
 */
@SuppressWarnings("InfiniteLoopStatement")
public class BlockingQueueJavadocExample {

    private class Producer implements Runnable {
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

    private class Consumer implements Runnable {
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

    @Test
    public void main() {
        BlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }
}
