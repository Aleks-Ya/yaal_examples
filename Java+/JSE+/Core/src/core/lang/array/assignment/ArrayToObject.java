package core.lang.array.assignment;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Присвоение массива переменной Object.
 */
public class ArrayToObject {
    @Test
    public void object() throws Exception {
        int[] a = {1, 2};

        Object o = a;
        Assert.assertTrue(o.getClass().isArray());
        Assert.assertEquals("class [I", o.getClass().toString());

        int[] b = (int[]) o;
        Assert.assertArrayEquals(a, b);
    }
}