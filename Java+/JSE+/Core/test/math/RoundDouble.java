package math;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RoundDouble {
    @Test
    public void round() {
        double value = 10.12345;
        double finalValue = Math.round(value * 100.0) / 100.0;
        assertThat(finalValue, equalTo(10.12));
    }
}
