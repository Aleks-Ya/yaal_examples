package intuit.combinatorics.reccurent;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PositiveNegativeNumsTest {
    private static final Function<Integer, Integer> func = new PositiveNegativeNums();

    @Test
    void good() {
        assertThat(func.apply(0), equalTo(0));
        assertThat(func.apply(1), equalTo(1));
        assertThat(func.apply(2), equalTo(-2));
        assertThat(func.apply(3), equalTo(3));
        assertThat(func.apply(4), equalTo(-4));
        assertThat(func.apply(5), equalTo(5));
        assertThat(func.apply(6), equalTo(-6));
    }

    @Test
    void noStackOverflow() {
        func.apply(1_000_000);
    }
}