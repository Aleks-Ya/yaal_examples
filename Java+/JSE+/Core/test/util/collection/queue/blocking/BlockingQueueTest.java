package util.collection.queue.blocking;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


class BlockingQueueTest {
    @Test
    void test() throws InterruptedException {
        BlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.put(1);
        q.put(2);
        assertThat(q.take(), equalTo(1));
        assertThat(q.take(), equalTo(2));
    }
}
