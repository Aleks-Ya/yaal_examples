package init;

import org.junit.Test;

import java.util.Arrays;

/**
 * Лишняя запятая в литерале.
 */
public class TrailingComma {

    /**
     * Одна запятая не является ошибкой.
     */
    @Test
    public void one() {
        int[][] array = {{1, 2, 3}, {0, 0, 0,},};
        System.out.println(Arrays.deepToString(array));
    }

    /**
     * Две запятые - ошибка компиляции.
     */
    @Test
    public void two() {
        //Ошибка компиляции illegal start of expression
        //int[][] array = {{1, 2, 3}, {0, 0, 0,},,};
    }
}