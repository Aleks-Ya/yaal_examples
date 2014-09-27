package init;

import org.junit.Test;

/**
 * Использование null при инициализации массива.
 */
public class NullElement {
    @Test
    public void main() {
        /* Массив ссылок */
        String[] arr = {null, null};
        
        /* Массив примитивов */
        //compile error: incompatible types
        //int[] arr2 = {null, null};
    }
}