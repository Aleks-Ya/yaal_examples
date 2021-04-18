package util.collection.array_list.add;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Добавление элементов в ArrayList в указанную позицию.
 */
public class AddToPosition {

    /**
     * Вставка между существующими элементами.
     */
    @Test
    public void betweenExists() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("c");
        assertEquals("[a, c]", list.toString());
        list.add(1, "b");
        assertEquals("[a, b, c]", list.toString());
    }

    /**
     * Вставка элемента в конец коллекции.
     */
    @Test
    public void toEnd() {
        List<String> list = new ArrayList<>();
        list.add("a");
        assertEquals("[a]", list.toString());
        list.add(1, "b");
        assertEquals("[a, b]", list.toString());
    }

    /**
     * Вставка элемента за пределы коллекции приводит к IndexOutOfBoundsException.
     * Вызов ArrayList#ensureCapacity не помогает.
     */
    @Test
    public void afterEndException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            ArrayList<String> list = new ArrayList<>();
            list.add("a");
            assertEquals("[a]", list.toString());
            list.ensureCapacity(100); //не помогает
            int position = list.size() + 1;
            list.add(position, "b");
            assertEquals("[a, b, c]", list.toString());
        });
    }
}