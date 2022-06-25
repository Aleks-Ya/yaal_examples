package lang.array.init;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Использование null при инициализации массива.
 */
class NullElementTest {

    /**
     * Одномерный массив.
     */
    @Test
    void oneDimension() {
        String[] arr = {null, null};
        assertEquals(2, arr.length);
    }

    /**
     * Многомерный массив.
     */
    @Test
    void multiDimensions() {
        String[][] arr = {
                null,
                null,
                {null, null}
        };
        assertEquals(3, arr.length);
        assertEquals(2, arr[2].length);
    }
}