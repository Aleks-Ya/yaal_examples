package core.lang.array.assignment;

import org.junit.Assert;
import org.junit.Test;

/**
 * Присвоение переменной массива суперкласса
 * массива дочернего класса.
 */
public class SubClass {

    /**
     * Примитивные типы.
     */
    @Test
    public void primitive() throws Exception {
        long[] a;
        int[] b = {1};

        //Compile errors:
        //a = b;
        //a = (long[]) b;
        //Assert.assertArrayEquals(a, b);
    }

    /**
     * Ссылочные типы.
     */
    @Test
    public void reference() throws Exception {
        Exception[] a;
        RuntimeException[] b = {new RuntimeException()};
        a = b;
        Assert.assertArrayEquals(a, b);
    }
}
