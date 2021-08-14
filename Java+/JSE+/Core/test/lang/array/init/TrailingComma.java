package lang.array.init;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Лишняя запятая в литерале.
 */
class TrailingComma {

    /**
     * Одна запятая не является ошибкой.
     */
    @Test
    void one() {
        int[][] array = {{1, 2, 3}, {0, 0, 0,},};
        System.out.println(Arrays.deepToString(array));
    }

    /**
     * Две запятые - ошибка компиляции.
     */
    @Test
    void two() {
        //Ошибка компиляции illegal start of expression
        //int[][] array = {{1, 2, 3}, {0, 0, 0,},,};
    }
}