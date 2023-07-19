package util.concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedBlockingQueueTest {
    @Test
    void put() throws InterruptedException {
        BlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        q.put(1);
        q.put(2);
        assertThat(q.take()).isEqualTo(1);
        assertThat(q.take()).isEqualTo(2);
    }
}
