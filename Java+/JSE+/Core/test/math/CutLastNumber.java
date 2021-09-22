package math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Задача: отрезать последнюю цифру от числа Double.
 */
class CutLastNumber {

    @Test
    void testName() {
        assertEquals(0, cutLastNumber(0.0), 0);
        assertEquals(0, cutLastNumber(0.1), 0);
        assertEquals(.45, cutLastNumber(.456), 0);
        assertEquals(123.45, cutLastNumber(123.456), 0);
        assertEquals(123, cutLastNumber(123.4), 0);
        assertEquals(12, cutLastNumber(123D), 0);
    }

    /**
     * Реализация через работу со строками.
     */
    private double cutLastNumber(final Double src) {
        var s = src.toString();
        while (s.endsWith("0") || s.endsWith(".")) {
            s = cutLastChar(s);
        }
        var result = "0";
        if (s.length() > 1) {
            result = cutLastChar(s);
            if (result.endsWith(".")) {
                result = cutLastChar(result);
            }
        }
        return Double.parseDouble(result);
    }

    private String cutLastChar(String s) {
        return s.substring(0, s.length() - 1);
    }
}