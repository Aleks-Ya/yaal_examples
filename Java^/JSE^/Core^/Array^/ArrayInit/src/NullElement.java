import java.util.Arrays;
import static java.lang.System.out;

/**
 * Использование null при инициализации массива.
 */
public class NullElement {
    public static void main(String[] args) {
        /* Массив ссылок */
        String[] arr = {null, null};
        
        /* Массив примитивов */
        //compile error: incompatible types
        //int[] arr2 = {null, null};
    }
}