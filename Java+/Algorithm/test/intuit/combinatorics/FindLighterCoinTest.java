package intuit.combinatorics;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Алгоритм поиска легкой монеты.
 */
public class FindLighterCoinTest {

    @Test
    void oneCoin() {
        var e = assertThrows(IllegalArgumentException.class,
                () -> FindLighterCoin.findLighterCoinIndex(new int[]{1}));
        assertThat(e.getMessage(), equalTo("Количество элементов должно быть больше 1"));
    }

    @Test
    void allCoinsEquals() {
        var e = assertThrows(IllegalArgumentException.class,
                () -> assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{10, 10, 10, 10}), equalTo(2)));
        assertThat(e.getMessage(), equalTo("Одна монета должна быть легче. Остальные монеты дожны быть одинакового веса."));
    }

    @Test
    void evenCoins() {
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{10, 10, 5, 10}), equalTo(2));
    }

    @Test
    void evenCoinsLong() {
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{1, 2, 2, 2, 2, 2, 2, 2}), equalTo(0));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 1, 2, 2, 2, 2, 2, 2}), equalTo(1));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 1, 2, 2, 2, 2, 2}), equalTo(2));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 1, 2, 2, 2, 2}), equalTo(3));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 1, 2, 2, 2}), equalTo(4));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 2, 1, 2, 2}), equalTo(5));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 2, 2, 1, 2}), equalTo(6));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 2, 2, 2, 1}), equalTo(7));
    }

    @Test
    void oddCoins() {
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{10, 10, 5}), equalTo(2));
    }

    @Test
    void oddCoinsLong() {
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{1, 2, 2, 2, 2, 2, 2}), equalTo(0));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 1, 2, 2, 2, 2, 2}), equalTo(1));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 1, 2, 2, 2, 2}), equalTo(2));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 1, 2, 2, 2}), equalTo(3));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 1, 2, 2}), equalTo(4));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 2, 1, 2}), equalTo(5));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 2, 2, 1}), equalTo(6));
    }

}
