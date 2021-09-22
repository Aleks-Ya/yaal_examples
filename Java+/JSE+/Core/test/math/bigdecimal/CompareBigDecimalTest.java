package math.bigdecimal;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CompareBigDecimalTest {
    @Test
    void compare() {
        var d1 = new BigDecimal(100.2);
        var d2 = new BigDecimal(100.2);
        assertEquals(d1, d2);
    }

    @Test
    void compare2() {
        var d1 = new BigDecimal(100.2);
        var d2 = new BigDecimal(50.1).add(new BigDecimal(50.1));
        assertTrue(d1.compareTo(d2) == 0);
        assertNotEquals(d1, d2);
    }
}
