package util.collection.array_list.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Использование Iterator для получения элементов ArrayList.
 */
class IteratorUsage {
    private final List<Character> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        list.add('a');
        list.add('b');
        list.add('c');
    }

    /**
     * Обход коллекции.
     */
    @Test
    void next() {
        var it = list.iterator();
        var sb = new StringBuilder();
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
    void remove() {
        var it = list.iterator();
        var sb = new StringBuilder();
        while (it.hasNext()) {
            var next = it.next();
            if (next.equals('b')) {
                it.remove();
            }
            sb.append(next);
        }
        assertEquals("[a, c]", list.toString());
        assertEquals("abc", sb.toString());
    }

}
