package util.collection.queue.priority;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;


class PriorityQueueTest {
    @Test
    void naturalOrder() {
        var q = new PriorityQueue<String>(3);
        q.add("c");
        q.add("a");
        q.add("b");

        assertThat(q.remove()).isEqualTo("a");
        assertThat(q.remove()).isEqualTo("b");
        assertThat(q.remove()).isEqualTo("c");
    }

    @Test
    void comparator() {
        var comparator = (Comparator<String>) (o1, o2) -> Integer.valueOf(o2.length()).compareTo(o1.length());
        var q = new PriorityQueue<String>(comparator);
        q.add("bb");
        q.add("ccc");
        q.add("a");

        assertThat(q.remove()).isEqualTo("ccc");
        assertThat(q.remove()).isEqualTo("bb");
        assertThat(q.remove()).isEqualTo("a");
    }
}
