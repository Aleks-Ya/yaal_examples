import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;

public class NumberAssert {
    @Test
    public void doubleNumbers() {
        double d = 1.5;
        assertThat(d, greaterThan(1D));
    }

    @Test
    public void close() {
        assertThat(1020D, closeTo(1000D, 50D));
    }
}