package lang.array.assignment;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Присвоение переменной массива суперкласса
 * массива дочернего класса.
 */
class SubClass {

    /**
     * Примитивные типы.
     */
    @Test
    void primitive() {
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
    void reference() {
        Exception[] a;
        RuntimeException[] b = {new RuntimeException()};
        a = b;
        Assert.assertArrayEquals(a, b);
    }
}
