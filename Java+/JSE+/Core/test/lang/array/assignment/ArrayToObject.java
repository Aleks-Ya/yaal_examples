package assignment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Присвоение массива переменной Object.
 */
public class ArrayToObject {
    @Test
    public void object() throws Exception {
        int[] a = {1, 2};

        Object o = a;
        assertTrue(o.getClass().isArray());
        assertEquals("class [I", o.getClass().toString());

        int[] b = (int[]) o;
        assertArrayEquals(a, b);
    }
}