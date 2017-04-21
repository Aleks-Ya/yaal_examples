import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

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