package util.collection.array_list.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Использование Iterator для получения элементов ArrayList.
 */
public class IteratorUsage {
    private final List<Character> list = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        list.add('a');
        list.add('b');
        list.add('c');
    }

    /**
     * Обход коллекции.
     */
    @Test
    public void next() {
        Iterator<Character> it = list.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        assertEquals("[a, b, c]", list.toString());
        assertEquals("abc", sb.toString());
    }

    /**
     * Удаление элемента во время обхода коллекции.
     */
    @Test
    public void remove() {
        Iterator<Character> it = list.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            Character next = it.next();
            if (next.equals('b')) {
                it.remove();
            }
            sb.append(next);
        }
        assertEquals("[a, c]", list.toString());
        assertEquals("abc", sb.toString());
    }

}
