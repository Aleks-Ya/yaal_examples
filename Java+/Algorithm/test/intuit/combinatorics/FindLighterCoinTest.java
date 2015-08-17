package intuit.combinatorics;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Алгоритм поиска легкой монеты.
 */
public class FindLighterCoinTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void oneCoin() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Количество элементов должно быть больше 1");
        FindLighterCoin.findLighterCoinIndex(new int[]{1});
    }

    @Test
    public void allCoinsEquals() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Одна монета должна быть легче. Остальные монеты дожны быть одинакового веса.");
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{10, 10, 10, 10}), equalTo(2));
    }

    @Test
    public void evenCoins() {
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{10, 10, 5, 10}), equalTo(2));
    }

    @Test
    public void evenCoinsLong() {
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
    public void oddCoins() {
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{10, 10, 5}), equalTo(2));
    }

    @Test
    public void oddCoinsLong() {
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{1, 2, 2, 2, 2, 2, 2}), equalTo(0));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 1, 2, 2, 2, 2, 2}), equalTo(1));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 1, 2, 2, 2, 2}), equalTo(2));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 1, 2, 2, 2}), equalTo(3));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 1, 2, 2}), equalTo(4));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 2, 1, 2}), equalTo(5));
        assertThat(FindLighterCoin.findLighterCoinIndex(new int[]{2, 2, 2, 2, 2, 2, 1}), equalTo(6));
    }

}
