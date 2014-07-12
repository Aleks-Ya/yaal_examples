import java.util.Arrays;
import static java.lang.System.out;

/**
 * Скомпилируется, но не выполнится.
 */
public class Compile {
    public static void main(String[] args) {
        int[] arr = new int[1];
        out.println(arr[-10]);
        out.println(arr[100]);
    }
}