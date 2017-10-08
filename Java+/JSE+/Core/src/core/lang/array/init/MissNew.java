package core.lang.array.init;

import org.junit.Test;

/**
 * Инициализация массива без new.
 */
public class MissNew {

    /**
     * В одну строку - ОК.
     */
    @Test
    public void oneLine() {
        int intArray[] = {0, 1};
        String[] strArray = {"Summer", "Winter"};
        int multiArray[][] = {{0, 1}, {3, 4, 5}};
    }

    /**
     * В две строки только с new, иначе ошибка компиляции.
     */
    @Test
    public void twoLines() {
        int intArray[];
        intArray = new int[]{0, 1};

        String[] strArray;
        strArray = new String[]{"Summer", "Winter"};

        int multiArray[][];
        multiArray = new int[][]{{0, 1}, {3, 4, 5}};
    }
}