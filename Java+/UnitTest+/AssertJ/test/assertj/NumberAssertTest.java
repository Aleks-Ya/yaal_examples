package assertj;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberAssertTest {
    @Test
    void closeTo() {
        var n = 1.5678;
        assertThat(n)
                .isEqualTo(1.5678)
                .isCloseTo(1.56, Offset.offset(0.009))
                .isNotCloseTo(1.56, Offset.offset(0.0001));
    }
}
