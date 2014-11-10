package withdrawal;

/**
 * Расчет положения фокуса.
 */
class Focus<T> {
    private final T[][] matrix;
    private static final int X_MIN = 0;
    private static final int Y_TOP = 0;
    private final int xMax;
    private final int yBottom;
    private int xFocus;
    private int yFocus;

    Focus(T[][] matrix) {
        this.matrix = matrix;
        yBottom = matrix.length - 1;
        int max = 0;
        for (T[] row : matrix) {
            if (row.length > max) {
                max = row.length;
            }
        }
        xMax = max;
    }

    void left() {
        if (X_MIN > xFocus) {
            if (nearLeft() != null) {
                xFocus--;
            }
        }
    }

    /**
     * Ближайшая непустая ячейка слева.
     */
    private T nearLeft() {
        T[] row = matrix[yFocus];
        for (int i = xFocus; i >= X_MIN; i--) {
            if (row[i] != null) {
                return row[i];
            }
        }
        return null;
    }

    void right() {

    }

    void down() {
        if (yFocus < yBottom) {
            for (int rowIndex = yFocus + 1; rowIndex <= yBottom; rowIndex++) {
                T[] row = matrix[rowIndex];
                if (row.length > xFocus && row[xFocus] != null) {
                    yFocus = rowIndex;
                }
            }
        }
    }

    void up() {

    }

    T selected() {
        return matrix[yFocus][xFocus];
    }
}