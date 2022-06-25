package util.collection.array_list.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Использование ListIterator для обхода ArrayList.
 * todo Методы set(), add()
 */
class ListIteratorUsageTest {
    private final List<Character> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        list.add('a');
        list.add('b');
        list.add('c');
    }

    /**
     * Обход коллекции в прямом направлении.
     */
    @Test
    void next() {
        //В прямом направлении
        var it = list.listIterator();
        var indexesNext = new StringBuilder();
        var sbNext = new StringBuilder();
        while (it.hasNext()) {
            indexesNext.append(it.nextIndex());
            sbNext.append(it.next());
        }
        assertEquals("[a, b, c]", list.toString());
        assertEquals("012", indexesNext.toString());
        assertEquals("abc", sbNext.toString());

        //в обратном направлении
        var indexesPrevious = new StringBuilder();
        var sbPrevious = new StringBuilder();
        while (it.hasPrevious()) {
            indexesPrevious.append(it.previousIndex());
            sbPrevious.append(it.previous());
        }

        assertEquals("[a, b, c]", list.toString());
        assertEquals("210", indexesPrevious.toString());
        assertEquals("cba", sbPrevious.toString());

    }

    /**
     * Удаление элемента во время обхода коллекции.
     */
    @Test
    void remove() {
        var it = list.listIterator();
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
