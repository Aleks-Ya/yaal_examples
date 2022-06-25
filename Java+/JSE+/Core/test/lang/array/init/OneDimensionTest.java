package lang.array.init;

import org.junit.jupiter.api.Test;

/**
 * Инициализация одномерных массивов.
 */
class OneDimensionTest {
    @Test
    void main() {
        {
            String[] arr = {"a"};
            String s = arr[0];
        }
        {
            //Compile error:
            //String s = {"a"}[0];
            String s = new String[]{"a"}[0];
        }
    }
}