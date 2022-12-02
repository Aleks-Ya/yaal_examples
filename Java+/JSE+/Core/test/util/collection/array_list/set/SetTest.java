package util.collection.array_list.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Замена элементов коллекции ArrayList.
 */
class SetTest {
    private final List<Character> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        list.add('a');
        list.add('b');
        list.add('c');
    }

    /**
     * Замена существующего элемента.
     */
    @Test
    void set() {
        assertThat(list).hasToString("[a, b, c]");
        list.set(1, 'd');
        assertThat(list).hasToString("[a, d, c]");
    }

    /**
     * Вставить в конец листа нельзя.
     */
    @Test
    void setNotExists() {
        assertThat(list).hasToString("[a, b, c]");
        assertThatThrownBy(() -> list.set(3, 'd')).isInstanceOf(IndexOutOfBoundsException.class);
    }
}
