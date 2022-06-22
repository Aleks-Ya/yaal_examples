package util.collection.queue.blocking;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

class BlockingQueueTest {
    @Test
    void test() throws InterruptedException {
        BlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.put(1);
        q.put(2);
        assertThat(q.take()).isEqualTo(1);
        assertThat(q.take()).isEqualTo(2);
    }
}
