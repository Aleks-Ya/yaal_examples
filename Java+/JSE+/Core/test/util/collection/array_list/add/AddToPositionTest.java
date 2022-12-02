package util.collection.array_list.add;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Добавление элементов в ArrayList в указанную позицию.
 */
class AddToPositionTest {

    /**
     * Вставка между существующими элементами.
     */
    @Test
    void betweenExists() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("c");
        assertThat(list).hasToString("[a, c]");
        list.add(1, "b");
        assertThat(list).hasToString("[a, b, c]");
    }

    /**
     * Вставка элемента в конец коллекции.
     */
    @Test
    void toEnd() {
        List<String> list = new ArrayList<>();
        list.add("a");
        assertThat(list).hasToString("[a]");
        list.add(1, "b");
        assertThat(list).hasToString("[a, b]");
    }

    /**
     * Вставка элемента за пределы коллекции приводит к IndexOutOfBoundsException.
     * Вызов ArrayList#ensureCapacity не помогает.
     */
    @Test
    void afterEndException() {
        assertThatThrownBy(() -> {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertThat(list).hasToString("[a]");
            list.ensureCapacity(100); //не помогает
            int position = list.size() + 1;
            list.add(position, "b");
            assertThat(list).hasToString("[a, b, c]");
        }).isInstanceOf(IndexOutOfBoundsException.class);
    }
}