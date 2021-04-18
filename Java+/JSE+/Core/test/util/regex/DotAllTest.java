package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Флаг DOTALL: метасимвол . совпадает с line terminators.
 */
public class DotAllTest {
    /**
     * Конструкция "(?s)".
     */
    @Test
    public void flag() {
        assertTrue("a\nb".matches("(?s).*"));
        assertFalse("a\nb".matches(".*"));
    }

    /**
     * Константа Pattern.CASE_INSENSITIVE.
     */
    @Test
    public void magicConstant() {
        assertTrue(Pattern.compile(".*", Pattern.DOTALL).matcher("a\nb").matches());
        assertFalse(Pattern.compile(".*").matcher("a\nb").matches());
    }
}
