package lang.array.assignment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Присвоение массива переменной Object.
 */
class ArrayToObjectTest {
    @Test
    void object() {
        int[] a = {1, 2};

        Object o = a;
        assertTrue(o.getClass().isArray());
        assertEquals("class [I", o.getClass().toString());

        int[] b = (int[]) o;
        assertArrayEquals(a, b);
    }
}