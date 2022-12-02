package util.collection.array_list.delete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Удаление элементов коллекции ArrayList по индексу.
 */
class DeleteByIndexTest {
    private final List<Character> charList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        charList.add('a');
        charList.add('b');
        charList.add('c');
    }

    /**
     * Удаление элемента по индексу.
     */
    @Test
    void byIndex() {
        assertThat(charList).hasToString("[a, b, c]");
        char b = charList.remove(1);
        assertThat(b).isEqualTo('b');
        assertThat(charList).hasToString("[a, c]");
    }
}