package knuth.first;

/**
 * Алгоритм Эвклида (стр. 28).
 */
public class GreatestCommonDivisor {
    public static int calculate(int m, int n) {
        assert (m > 0);
        assert (n > 0);

        int reminder;
        int r;
        do {
            r = m % n;
            reminder = m = n;
            n = r;
        } while (r != 0);

        return reminder;
    }
}
