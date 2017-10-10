package lang.generics.methods;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Write a generic method to exchange the positions of two different elements in an array.
 * Упражнения из Java Tutorial по Generics:
 * https://docs.oracle.com/javase/tutorial/java/generics/QandE/generics-questions.html
 */
public class SwapArray {
    public <T> void swapElements(T[] array, int index1, int index2) {
        assert (array != null);
        assert (index1 < array.length);
        assert (index2 < array.length);

        T element = array[index1];
        array[index1] = array[index2];
        array[index2] = element;
    }

    @Test
    public void test() {
        String[] strArr = {"a", "b", "c"};
        swapElements(strArr, 0, 2);
        assertArrayEquals(new String[]{"c", "b", "a"}, strArr);
    }
}
