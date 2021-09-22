package util.collection.queue.priority;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


class PriorityQueueTest {
    @Test
    void naturalOrder() {
        var q = new PriorityQueue<String>(3);
        q.add("c");
        q.add("a");
        q.add("b");

        assertThat(q.remove(), equalTo("a"));
        assertThat(q.remove(), equalTo("b"));
        assertThat(q.remove(), equalTo("c"));
    }

    @Test
    void comparator() {
        var comparator = (Comparator<String>) (o1, o2) -> Integer.valueOf(o2.length()).compareTo(o1.length());
        var q = new PriorityQueue<String>(comparator);
        q.add("bb");
        q.add("ccc");
        q.add("a");

        assertThat(q.remove(), equalTo("ccc"));
        assertThat(q.remove(), equalTo("bb"));
        assertThat(q.remove(), equalTo("a"));
    }
}
