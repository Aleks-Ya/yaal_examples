package lang.array.init;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

/**
 * Скомпилируется, но не выполнится.
 */
class CompileTest {
    @Test
    void main() {
        try {
            int[] arr = new int[1];
            out.println(arr[-10]);
            out.println(arr[100]);
        } catch (ArrayIndexOutOfBoundsException e) {
            out.println(e);
        }
    }
}