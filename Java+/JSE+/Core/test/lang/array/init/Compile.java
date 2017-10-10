package init;

import org.junit.Test;

import static java.lang.System.out;

/**
 * Скомпилируется, но не выполнится.
 */
public class Compile {
    @Test
    public void main() {
        try {
            int[] arr = new int[1];
            out.println(arr[-10]);
            out.println(arr[100]);
        } catch (ArrayIndexOutOfBoundsException e) {
            out.println(e);
        }
    }
}