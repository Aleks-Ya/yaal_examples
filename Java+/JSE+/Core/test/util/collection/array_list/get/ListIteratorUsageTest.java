package util.collection.array_list.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(list).hasToString("[a, b, c]");
        assertThat(indexesNext).hasToString("012");
        assertThat(sbNext).hasToString("abc");

        //в обратном направлении
        var indexesPrevious = new StringBuilder();
        var sbPrevious = new StringBuilder();
        while (it.hasPrevious()) {
            indexesPrevious.append(it.previousIndex());
            sbPrevious.append(it.previous());
        }

        assertThat(list).hasToString("[a, b, c]");
        assertThat(indexesPrevious).hasToString("210");
        assertThat(sbPrevious).hasToString("cba");

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
        assertThat(list).hasToString("[a, c]");
        assertThat(sb).hasToString("abc");
    }

}
