package math;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Задача: отрезать последнюю цифру от числа Double.
 */
class CutLastNumberTest {

    @Test
    void testName() {
        assertThat(cutLastNumber(0.0)).isEqualTo(0);
        assertThat(cutLastNumber(0.1)).isEqualTo(0);
        assertThat(cutLastNumber(.456)).isEqualTo(.45);
        assertThat(cutLastNumber(123.456)).isEqualTo(123.45);
        assertThat(cutLastNumber(123.4)).isEqualTo(123);
        assertThat(cutLastNumber(123D)).isEqualTo(12);
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