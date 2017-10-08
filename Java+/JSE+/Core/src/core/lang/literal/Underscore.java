package core.lang.literal;

import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

/**
 * Подчеркивание в чиловых литералах.
 */
public class Underscore {

    /**
     * Одно подчеркивание в литералах разных типов данных.
     */
    @Test
    public void one() {
        out.println("Части числа отделены подчеркиванием:");
        out.printf("Двоичная: 0b10_01 -> %d (нельзя 0b_1001 и )%n", 0b10_01);
        out.printf("Восмеричная: 0_7_34_2 -> %d (нельзя _07342)%n", 0_7_34_2);
        out.printf("Десятичная: 30_429 -> %d (нельзя _30429)%n", 30_429);
        out.printf("Шестнадцатеричная: 0xa_3_f -> %d (нельзя 0x_a3f)%n%n", 0xa_3_f);
    }

    /**
     * Несколько подчеркиваний подряд.
     */
    @Test
    public void multipleUnderscore() {
        Assert.assertEquals(39, 3_____9);
    }
}