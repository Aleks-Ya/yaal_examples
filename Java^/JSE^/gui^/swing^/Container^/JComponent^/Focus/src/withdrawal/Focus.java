package withdrawal;

/**
 * Расчет положения фокуса.
 */
class Focus<T> {
    private final T[][] matrix;
    private static final int X_LEFT_LIMIT = 0;
    private static final int Y_TOP = 0;
    private final int yBottom;
    private int xFocus;
    private int yFocus;
    private int prevXFocus;
    private int prevYFocus;

    Focus(T[][] matrix) {
        this.matrix = matrix;
        yBottom = matrix.length - 1;
    }

    void left() {
    }

    void right() {
        T[] row = matrix[yFocus];
        for (int x = xFocus + 1; x < row.length; x++) {
            if (row[x] != null) {
                prevXFocus = xFocus;
                prevYFocus = yFocus;
                xFocus = x;
                break;
            }
        }
    }

    void down() {
        if (yFocus < yBottom) {
            for (int rowIndex = yFocus + 1; rowIndex <= yBottom; rowIndex++) {
                T[] row = matrix[rowIndex];
                if (row.length > xFocus && row[xFocus] != null) {
                    prevXFocus = xFocus;
                    prevYFocus = yFocus;
                    yFocus = rowIndex;
                    break;
                }
            }
        }
    }

    void up() {
    }

    T selected() {
        return matrix[yFocus][xFocus];
    }

    T prevSelected() {
        return matrix[prevYFocus][prevXFocus];
    }
}