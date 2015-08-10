package intuit.combinatorics;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Алгоритм поиска легкой монеты.
 */
public class FindLighterCoin {
    static int findLighterCoin(int[] coins) {
        if (coins.length < 2) {
            throw new IllegalArgumentException("Количество элементов должно быть больше 1");
        }
        return 0;
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void oneCoin() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Количество элементов должно быть больше 1");
        findLighterCoin(new int[]{1});
    }
}
