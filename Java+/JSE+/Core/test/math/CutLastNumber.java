package math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Задача: отрезать последнюю цифру от числа Double.
 */
public class CutLastNumber {

    /**
     * Реализация через работу со строками.
     */
    private double cutLastNumber(final Double src) {
        String s = src.toString();
        while (s.endsWith("0") || s.endsWith(".")) {
            s = cutLastChar(s);
        }
        String result = "0";
        if (s.length() > 1) {
            result = cutLastChar(s);
            if (result.endsWith(".")) {
                result = cutLastChar(result);
            }
        }
        return Double.valueOf(result);
    }

    private String cutLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }

    @Test
    public void testName() throws Exception {
        assertEquals(0, cutLastNumber(0.0), 0);
        assertEquals(0, cutLastNumber(0.1), 0);
        assertEquals(.45, cutLastNumber(.456), 0);
        assertEquals(123.45, cutLastNumber(123.456), 0);
        assertEquals(123, cutLastNumber(123.4), 0);
        assertEquals(12, cutLastNumber(123D), 0);
    }
}