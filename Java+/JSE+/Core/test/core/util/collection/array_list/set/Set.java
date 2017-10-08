package core.util.collection.array_list.set;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Замена элементов коллекции ArrayList.
 */
public class Set {
    private final List<Character> list = new ArrayList<>();

    @Before
    public void setUp() {
        list.add('a');
        list.add('b');
        list.add('c');
    }

    /**
     * Замена существующего элемента.
     */
    @Test
    public void set() {
        assertEquals("[a, b, c]", list.toString());
        list.set(1, 'd');
        assertEquals("[a, d, c]", list.toString());
    }

    /**
     * Вставить в конец листа нельзя.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void setNotExists() {
        assertEquals("[a, b, c]", list.toString());
        list.set(3, 'd');
    }
}
