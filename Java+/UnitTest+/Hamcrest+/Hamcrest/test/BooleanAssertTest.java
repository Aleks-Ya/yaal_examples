import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BooleanAssertTest {
    @Test
    void booleans() {
        assertThat(Boolean.TRUE, is(true));
    }
}