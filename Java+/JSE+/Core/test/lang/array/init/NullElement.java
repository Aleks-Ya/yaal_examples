package init;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Использование null при инициализации массива.
 */
public class NullElement {

    /**
     * Одномерный массив.
     */
    @Test
    public void oneDimension() {
        String[] arr = {null, null};
        assertEquals(2, arr.length);
    }

    /**
     * Многомерный массив.
     */
    @Test
    public void multiDimensions() {
        String[][] arr = {
                null,
                null,
                {null, null}
        };
        assertEquals(3, arr.length);
        assertEquals(2, arr[2].length);
    }
}