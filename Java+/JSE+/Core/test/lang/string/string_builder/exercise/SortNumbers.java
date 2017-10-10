package lang.string.string_builder.exercise;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Упорядочить коллекцию чисел по возрастанию.
 */
public class SortNumbers {
    @Test
    public void sort() {
        //Пустая коллекция
        List<Long> empty = new ArrayList<>(0);
        sorting(empty);
        assertEquals("[]", empty.toString());

        //Коллекция из 2-х элементов
        List<Long> two = Arrays.asList(2L, 1L);
        sorting(two);
        assertEquals("[1, 2]", two.toString());

        //Коллекция из большого числа элементов
        List<Long> more = Arrays.asList(2L, 1L, 4L, 3L);
        sorting(more);
        assertEquals("[1, 2, 3, 4]", more.toString());
    }

    private void sorting(List<Long> source) {
        for (int current = 0; current < source.size() - 1; current++) {
            int next = current + 1;
            Long f = source.get(current);
            Long s = source.get(next);
            if (f > s) {
                source.set(current, s);
                source.set(next, f);
            }
        }
    }
}