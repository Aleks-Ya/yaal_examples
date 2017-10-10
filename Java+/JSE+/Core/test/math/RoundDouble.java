package math;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RoundDouble {
    @Test
    public void round() {
        double value = 10.12345;
        double finalValue = Math.round(value * 100.0) / 100.0;
        assertThat(finalValue, equalTo(10.12));
    }
}
