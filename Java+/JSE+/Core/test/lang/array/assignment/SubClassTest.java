package lang.array.assignment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Присвоение переменной массива суперкласса
 * массива дочернего класса.
 */
class SubClassTest {

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
        assertThat(a).isEqualTo(b);
    }
}
