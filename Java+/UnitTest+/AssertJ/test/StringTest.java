import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {
    @Test
    public void string() {
        assertThat("a")
                .isEqualTo("a")
                .isNotEqualTo("b")
                .isIn("a", "b", "c");
    }
}
