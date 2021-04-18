package knuth.first;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class GreatestCommonDivisorTest {

    @Test
    public void testCalculate() {
        assertEquals(50, GreatestCommonDivisor.calculate(100, 50));
        assertEquals(50, GreatestCommonDivisor.calculate(50, 100));
        assertEquals(2, GreatestCommonDivisor.calculate(10, 4));
        assertEquals(2, GreatestCommonDivisor.calculate(4, 10));
        assertEquals(1, GreatestCommonDivisor.calculate(10, 3));
        assertEquals(1, GreatestCommonDivisor.calculate(3, 10));
        assertEquals(17, GreatestCommonDivisor.calculate(119, 544));
    }
}