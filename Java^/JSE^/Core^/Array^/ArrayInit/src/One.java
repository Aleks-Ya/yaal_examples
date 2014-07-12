import java.util.Arrays;
import static java.lang.System.out;

/**
 * Одномерные массивы.
 */
public class One {
    public static void main(String[] args) {
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