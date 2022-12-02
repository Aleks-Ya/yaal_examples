package lang.array.init;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(arr).hasSize(2);
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
        assertThat(arr.length).isEqualTo(3);
        assertThat(arr[2].length).isEqualTo(2);
    }
}