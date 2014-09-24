import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.System.out;

/**
 * Одномерные массивы.
 */
public class One {
    @Test
    public void main() {
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
