package lang.array.init;

import org.junit.jupiter.api.Test;

/**
 * Инициализация массива без new.
 */
class MissNewTest {

    /**
     * В одну строку - ОК.
     */
    @Test
    void oneLine() {
        int intArray[] = {0, 1};
        String[] strArray = {"Summer", "Winter"};
        int multiArray[][] = {{0, 1}, {3, 4, 5}};
    }

    /**
     * В две строки только с new, иначе ошибка компиляции.
     */
    @Test
    void twoLines() {
        int intArray[];
        intArray = new int[]{0, 1};

        String[] strArray;
        strArray = new String[]{"Summer", "Winter"};

        int multiArray[][];
        multiArray = new int[][]{{0, 1}, {3, 4, 5}};
    }
}