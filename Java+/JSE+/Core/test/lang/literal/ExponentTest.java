package lang.literal;

import org.junit.jupiter.api.Test;

/**
 * Знак экспоненты в литералах.
 */
class ExponentTest {

    /**
     * Обычная экспонента.
     */
    @Test
    void e() throws Exception {
        float f2 = 6.022137e23f;
        float f1 = 6.022137e+23f;
        float f3 = 6.022137e-23f;

        double d1 = 1e137;
        double d2 = 1e+9;
        double d3 = 1e-9;
    }

    /**
     * В HEX экспонента вместо E обозначается P (т.к. E - цифра).
     */
    @Test
    void p() throws Exception {
        double $ = 0XD_EP2F;
        System.out.print($);
    }
}