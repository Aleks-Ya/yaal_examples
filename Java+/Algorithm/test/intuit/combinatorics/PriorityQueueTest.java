package intuit.combinatorics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Очередью с приоритетом называется линейный список, который оперирует
 * в режиме "первым включается - с высшим приоритетом исключается";
 * иными словами, каждому элементу очереди сопоставлено некоторое число - приоритет.
 * Включения производятся в конец очереди, а исключения - в любом месте очереди,
 * поскольку исключаемый элемент - это всегда элемент с высшим приоритетом.
 * Нужно описать алгоритм (и его реализацию) включения и исключения для очередей с приоритетом.
 */
public class PriorityQueueTest {

    @Test
    public void test() {
        PriorityQueue<String> q = new PriorityQueueImpl<>();
        q.put("third", 1);
        q.put("first", 4);
        q.put("second", 2);
        q.put("to_end", -1);
        q.put("to_first", 5);
        q.put("to_middle", 3);
        q.put("same_priority", 3);
        q.put("to_end2", -2);
        q.put("before_end", 0);

        assertEquals("to_first first to_middle same_priority second third before_end to_end to_end2 ", priorityQueueToString(q));
    }

    private String priorityQueueToString(PriorityQueue<?> q) {
        StringBuilder sb = new StringBuilder();
        Object element = q.get();
        while (element != null) {
            sb.append(element).append(" ");
            element = q.get();
        }
        return sb.toString();
    }
}