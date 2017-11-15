import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BooleanAssert {
    @Test
    public void booleans() {
        assertThat(Boolean.TRUE, is(true));
    }
}