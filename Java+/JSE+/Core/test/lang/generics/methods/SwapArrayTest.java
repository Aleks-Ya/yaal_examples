package lang.generics.methods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Write a generic method to exchange the positions of two different elements in an array.
 * Упражнения из Java Tutorial по Generics:
 * https://docs.oracle.com/javase/tutorial/java/generics/QandE/generics-questions.html
 */
class SwapArrayTest {
    public <T> void swapElements(T[] array, int index1, int index2) {
        assert (array != null);
        assert (index1 < array.length);
        assert (index2 < array.length);

        T element = array[index1];
        array[index1] = array[index2];
        array[index2] = element;
    }

    @Test
    void test() {
        String[] strArr = {"a", "b", "c"};
        swapElements(strArr, 0, 2);
        assertThat(strArr).isEqualTo(new String[]{"c", "b", "a"});
    }
}
