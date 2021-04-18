package parameterized;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Example from the documentation:
 * https://github.com/junit-team/junit4/wiki/Parameterized-tests
 */
@RunWith(Parameterized.class)
public class FibonacciTest {
    private int fInput;
    private int fExpected;

    public FibonacciTest(int input, int expected) {
        fInput = input;
        fExpected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 0}, {1, 1}, {2, 1}, {3, 2}, {4, 3}, {5, 5}, {6, 8}
        });
    }

    @Test
    public void test() {
        assertEquals(fExpected, Fibonacci.compute(fInput));
    }

    private static class Fibonacci {
        static int compute(int n) {
            int result;
            if (n <= 1) {
                result = n;
            } else {
                result = compute(n - 1) + compute(n - 2);
            }
            return result;
        }
    }
}
