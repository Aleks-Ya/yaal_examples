package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Флаг DOTALL: метасимвол . совпадает с line terminators.
 */
class DotAllTest {
    /**
     * Конструкция "(?s)".
     */
    @Test
    void flag() {
        assertTrue("a\nb".matches("(?s).*"));
        assertFalse("a\nb".matches(".*"));
    }

    /**
     * Константа Pattern.CASE_INSENSITIVE.
     */
    @Test
    void magicConstant() {
        assertTrue(Pattern.compile(".*", Pattern.DOTALL).matcher("a\nb").matches());
        assertFalse(Pattern.compile(".*").matcher("a\nb").matches());
    }
}
