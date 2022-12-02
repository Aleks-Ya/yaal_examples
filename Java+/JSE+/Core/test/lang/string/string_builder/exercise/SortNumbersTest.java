package lang.string.string_builder.exercise;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Упорядочить коллекцию чисел по возрастанию.
 */
class SortNumbersTest {
    @Test
    void sort() {
        //Пустая коллекция
        List<Long> empty = new ArrayList<>(0);
        sorting(empty);
        assertThat(empty).hasToString("[]");

        //Коллекция из 2-х элементов
        List<Long> two = Arrays.asList(2L, 1L);
        sorting(two);
        assertThat(two).hasToString("[1, 2]");

        //Коллекция из большого числа элементов
        List<Long> more = Arrays.asList(2L, 1L, 4L, 3L);
        sorting(more);
        assertThat(more).hasToString("[1, 2, 3, 4]");
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