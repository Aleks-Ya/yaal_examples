package intuit.combinatorics;

import java.util.Arrays;

/**
 * Алгоритм поиска легкой монеты.
 */
public class FindLighterCoin {
    static int findLighterCoinIndex(int[] coins) {
        if (coins.length < 2) {
            throw new IllegalArgumentException("Количество элементов должно быть больше 1");
        }
        return findLighter(coins, 0, coins.length - 1);
    }

    private static int findLighter(int[] coins, int iStart, int iEnd) {
        int length = iEnd - iStart + 1;
        boolean isEven = length % 2 == 0;
        Integer iExcessCoin;
        int[] cLeft, cRight;
        int leftDisplacement, rightDisplacement, iLastCoin;
        if (isEven) {
            iExcessCoin = null;
            iLastCoin = iEnd;
        } else {
            iExcessCoin = iEnd;
            iLastCoin = iEnd - 1;
        }
        leftDisplacement = iStart;
        rightDisplacement = iStart + length / 2;
        cLeft = Arrays.copyOfRange(coins, leftDisplacement, leftDisplacement + length / 2);
        cRight = Arrays.copyOfRange(coins, rightDisplacement, iLastCoin + 1);
        if (cLeft.length > 1) {
            assert cLeft.length == cRight.length;
            int leftWeight = 0;
            int rightWeight = 0;
            for (int i = 0; i < cLeft.length; i++) {
                leftWeight += cLeft[i];
                rightWeight += cRight[i];
            }
            if (leftWeight < rightWeight) {
                return findLighter(coins, leftDisplacement, leftDisplacement + cLeft.length - 1);
            }
            if (leftWeight > rightWeight) {
                return findLighter(coins, rightDisplacement, rightDisplacement + cRight.length - 1);
            }
            if (iExcessCoin != null) {
                return iExcessCoin;
            } else {
                throw new IllegalArgumentException("Одна монета должна быть легче. " +
                        "Остальные монеты дожны быть одинакового веса.");
            }
        } else {
            if (isEven) {
                return cLeft[0] < cRight[0] ? leftDisplacement : rightDisplacement;
            } else {
                return iExcessCoin;
            }
        }
    }
}
