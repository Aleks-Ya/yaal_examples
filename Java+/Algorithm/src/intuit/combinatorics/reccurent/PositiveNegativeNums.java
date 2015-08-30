package intuit.combinatorics.reccurent;

import java.util.function.Function;

/**
 * Возвращаемые значения: 1, -2, 3, -4, 5, -6 ...
 */
public class PositiveNegativeNums implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer n) {
        assert n >= 0;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        Integer current = 0;
        Integer prev = 1;
        for (int i = 2; i <= n; i++) {
            int prevAbs = Math.abs(prev);
            int sign = prev / prevAbs;
            current = -sign * (prevAbs + 1);
            prev = current;
        }
        return current;
    }
}
