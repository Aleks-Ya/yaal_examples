package core.lang.array.init;

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
        Assert.assertEquals(2, arr.length);
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
        Assert.assertEquals(3, arr.length);
        Assert.assertEquals(2, arr[2].length);
    }
}