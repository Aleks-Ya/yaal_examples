package swing.Container.JComponent.Focus.withdrawal;

/**
 * Расчет положения фокуса.
 */
class Focus<T> {
    private final T[][] matrix;
    private static final int X_LEFT = 0;
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
        T[] row = matrix[yFocus];
        for (int x = xFocus - 1; x >= X_LEFT; x--) {
            if (row[x] != null) {
                prevXFocus = xFocus;
                prevYFocus = yFocus;
                xFocus = x;
                break;
            }
        }
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
            for (int y = yFocus + 1; y <= yBottom; y++) {
                T[] row = matrix[y];
                if (row.length > xFocus && row[xFocus] != null) {
                    prevXFocus = xFocus;
                    prevYFocus = yFocus;
                    yFocus = y;
                    break;
                }
            }
        }
    }

    void up() {
        for (int y = yFocus - 1; y >= Y_TOP; y--) {
            T[] row = matrix[y];
            if (row.length > xFocus && row[xFocus] != null) {
                prevXFocus = xFocus;
                prevYFocus = yFocus;
                yFocus = y;
                break;
            }
        }
    }

    T selected() {
        return matrix[yFocus][xFocus];
    }

    T prevSelected() {
        return matrix[prevYFocus][prevXFocus];
    }
}