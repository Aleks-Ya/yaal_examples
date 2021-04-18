package intuit.combinatorics;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactorialTest {

    @Test
    public void zero() {
        assertThat(Factorial.factorial(0), equalTo(1L));
    }

    @Test
    public void positive() {
        assertThat(Factorial.factorial(1), equalTo(1L));
        assertThat(Factorial.factorial(2), equalTo(2L));
        assertThat(Factorial.factorial(3), equalTo(6L));
        assertThat(Factorial.factorial(4), equalTo(24L));
        assertThat(Factorial.factorial(5), equalTo(120L));
    }

    @Test
    public void negative() {
        var e = assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-1));
        assertThat(e.getMessage(), equalTo("Number must be positive."));
    }
}