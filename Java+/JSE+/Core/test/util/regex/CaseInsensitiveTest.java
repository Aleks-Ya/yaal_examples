package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Поиск без учета регистра.
 */
class CaseInsensitiveTest {

    /**
     * Конструкция "(?i)".
     */
    @Test
    void flag() {
        assertTrue("A".matches("(?i)a"));
        assertTrue("\u0044".matches("(?i)\u0064"));
        assertFalse("A".matches("(?-i)a"));
    }

    /**
     * Константа Pattern.CASE_INSENSITIVE.
     */
    @Test
    void magicConstant() {
        assertTrue(Pattern.compile("a", Pattern.CASE_INSENSITIVE).matcher("A").matches());
        assertFalse(Pattern.compile("a").matcher("A").matches());
    }
}
