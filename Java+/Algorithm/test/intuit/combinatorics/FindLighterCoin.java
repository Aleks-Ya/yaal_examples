package intuit.combinatorics;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

/**
 * Алгоритм поиска легкой монеты.
 */
public class FindLighterCoin {
    static int findLighterCoin(int[] coins) {
        if (coins.length < 2) {
            throw new IllegalArgumentException("Количество элементов должно быть больше 1");
        }
        boolean isEven = coins.length % 2 == 0;
        Integer iExcessCoin;
        int[] left;
        int[] right;
        int iLastCoin;
        if (isEven) {
            iExcessCoin = null;
            iLastCoin = coins.length / 2;
        } else {
            iExcessCoin = coins.length - 1;
            iLastCoin = (coins.length - 1) / 2;

        }
        left = Arrays.copyOfRange(coins, 0, iLastCoin / 2 - 1);
        right = Arrays.copyOfRange(coins, iLastCoin / 2, coins.length - 1);
        while (left.length > 1) {
            assert left.length == right.length;
            int leftWeight = 0;
            int rightWeight = 0;
            for (int i = 0; i < left.length; i++) {
                leftWeight += left[i];
                rightWeight += right[i];
            }
            if (leftWeight < rightWeight) {
                left = Arrays.copyOfRange(left, 0, left.length / 2 - 1);
                right = Arrays.copyOfRange(left, left.length / 2, left.length - 1);
            } else if (leftWeight > rightWeight) {
                left = Arrays.copyOfRange(right, 0, right.length / 2 - 1);
                right = Arrays.copyOfRange(right, right.length / 2, right.length - 1);
            } else {
                if (iExcessCoin != null) {
                    return iExcessCoin;
                } else {
                    throw new IllegalArgumentException("Одна монета должна быть легче. " +
                            "Остальные монеты дожны быть одинакового веса.");
                }
            }
        }
        return 0;//todo закончить
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
