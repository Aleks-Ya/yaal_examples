package util.collection.array_list.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertEquals;

/**
 * Использование ListIterator для обхода ArrayList.
 * todo Методы set(), add()
 */
public class ListIteratorUsage {
    private final List<Character> list = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        list.add('a');
        list.add('b');
        list.add('c');
    }

    /**
     * Обход коллекции в прямом направлении.
     */
    @Test
    public void next() {
        //В прямом направлении
        ListIterator<Character> it = list.listIterator();
        StringBuilder indexesNext = new StringBuilder();
        StringBuilder sbNext = new StringBuilder();
        while (it.hasNext()) {
            indexesNext.append(it.nextIndex());
            sbNext.append(it.next());
        }
        assertEquals("[a, b, c]", list.toString());
        assertEquals("012", indexesNext.toString());
        assertEquals("abc", sbNext.toString());

        //в обратном направлении
        StringBuilder indexesPrevious = new StringBuilder();
        StringBuilder sbPrevious = new StringBuilder();
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
    public void remove() {
        ListIterator<Character> it = list.listIterator();
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
