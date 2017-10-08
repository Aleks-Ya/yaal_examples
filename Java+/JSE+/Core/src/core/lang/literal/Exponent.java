package core.lang.literal;

import org.junit.Test;

/**
 * Знак экспоненты в литералах.
 */
public class Exponent {

    /**
     * Обычная экспонента.
     */
    @Test
    public void e() throws Exception {
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
    public void p() throws Exception {
        double $ = 0XD_EP2F;
        System.out.print($);
    }
}