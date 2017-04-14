package math.bigdecimal;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CompareBigDecimalTest {
    @Test
    public void compare() {
        BigDecimal d1 = new BigDecimal(100.2);
        BigDecimal d2 = new BigDecimal(100.2);
        assertEquals(d1, d2);
    }

    @Test
    public void compare2() {
        BigDecimal d1 = new BigDecimal(100.2);
        BigDecimal d2 = new BigDecimal(50.1).add(new BigDecimal(50.1));
        assertTrue(d1.compareTo(d2) == 0);
        assertNotEquals(d1, d2);
    }
}
