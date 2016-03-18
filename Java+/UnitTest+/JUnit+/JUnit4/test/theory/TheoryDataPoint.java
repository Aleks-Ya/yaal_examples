package theory;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Использование теорий JUnit (Theory).
 * Генерация входных данных с помощью @DataPoint.
 */
@RunWith(Theories.class)
public class TheoryDataPoint {
    @DataPoint
    public static double POSITIVE_4 = 4;
    @DataPoint
    public static double POSITIVE_16 = 16;
    @DataPoint
    public static double NEGATIVE = -1;

    @Theory
    public void square(double n) {
        assumeTrue(n > 0);
        double sqrt = Math.sqrt(n);
        assertEquals(n, sqrt * sqrt, 1);
    }
}
