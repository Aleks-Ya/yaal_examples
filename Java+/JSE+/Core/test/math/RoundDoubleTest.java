package math;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoundDoubleTest {
    @Test
    void round() {
        var value = 10.12345;
        var finalValue = Math.round(value * 100.0) / 100.0;
        assertThat(finalValue).isEqualTo(10.12);
    }
}
