import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;

class NumberAssertTest {
    @Test
    void doubleNumbers() {
        var d = 1.5;
        assertThat(d, greaterThan(1D));
    }

    @Test
    void close() {
        assertThat(1020D, closeTo(1000D, 50D));
    }
}