package util.collection.array_list.delete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("[a, b, c]", charList.toString());
        char b = charList.remove(1);
        assertEquals('b', b);
        assertEquals("[a, c]", charList.toString());
    }
}