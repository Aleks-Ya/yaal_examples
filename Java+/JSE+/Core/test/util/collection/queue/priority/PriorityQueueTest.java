package util.collection.queue.priority;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class PriorityQueueTest {
    @Test
    public void naturalOrder() {
        PriorityQueue<String> q = new PriorityQueue<>(3);
        q.add("c");
        q.add("a");
        q.add("b");

        assertThat(q.remove(), equalTo("a"));
        assertThat(q.remove(), equalTo("b"));
        assertThat(q.remove(), equalTo("c"));
    }

    @Test
    public void comparator() {
        Comparator<String> comparator = (o1, o2) -> Integer.valueOf(o2.length()).compareTo(o1.length());
        PriorityQueue<String> q = new PriorityQueue<>(comparator);
        q.add("bb");
        q.add("ccc");
        q.add("a");

        assertThat(q.remove(), equalTo("ccc"));
        assertThat(q.remove(), equalTo("bb"));
        assertThat(q.remove(), equalTo("a"));
    }
}
