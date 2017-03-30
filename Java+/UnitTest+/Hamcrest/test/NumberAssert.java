import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class NumberAssert {
    @Test
    public void doubleNumbers() {
        double d = 1.5;
        assertThat(d, greaterThan(1D));
    }
}