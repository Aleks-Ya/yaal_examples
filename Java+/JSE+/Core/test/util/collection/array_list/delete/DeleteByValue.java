package util.collection.array_list.delete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Удаление элементов коллекции ArrayList по значению.
 */
public class DeleteByValue {
    private final List<Character> charList = new ArrayList<>();
    private final List<Integer> integerList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        charList.add('a');
        charList.add('b');
        charList.add('c');
        integerList.add(10);
        integerList.add(20);
        integerList.add(30);
        stringList.add("big");
        stringList.add("medium");
        stringList.add("small");
    }

    /**
     * Удаление элемента по значению.
     */
    @Test
    public void byValue() {
        assertEquals("[big, medium, small]", stringList.toString());
        boolean removed = stringList.remove("medium");
        assertTrue(removed);
        assertEquals("[big, small]", stringList.toString());
    }

    /**
     * Удаление элемента char по значению..
     */
    @Test
    public void byCharacterValue() {
        assertEquals("[a, b, c]", charList.toString());
        boolean removed = charList.remove(Character.valueOf('b'));
        assertTrue(removed);
        assertEquals("[a, c]", charList.toString());
    }

    /**
     * Удаление элемента char по значению..
     */
    @Test
    public void byIntegerValue() {
        assertEquals("[10, 20, 30]", integerList.toString());
        int index = 20;
        boolean removed = integerList.remove(Integer.valueOf(index));
        assertTrue(removed);
        assertEquals("[10, 30]", integerList.toString());
    }
}