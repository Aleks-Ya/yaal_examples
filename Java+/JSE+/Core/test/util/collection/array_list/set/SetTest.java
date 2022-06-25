package util.collection.array_list.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals("[a, b, c]", list.toString());
        list.set(1, 'd');
        assertEquals("[a, d, c]", list.toString());
    }

    /**
     * Вставить в конец листа нельзя.
     */
    @Test
    void setNotExists() {
        assertEquals("[a, b, c]", list.toString());
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, 'd'));
    }
}
