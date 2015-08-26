package intuit.combinatorics;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.rmi.server.ExportException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class FactorialTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Number must be positive.");
        Factorial.factorial(-1);
    }
}