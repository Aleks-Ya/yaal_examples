package util.regex;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Поиск без учета регистра.
 */
public class CaseInsensitiveTest {

    /**
     * Конструкция "(?i)".
     */
    @Test
    public void flag() {
        assertTrue("A".matches("(?i)a"));
        assertTrue("\u0044".matches("(?i)\u0064"));
        assertFalse("A".matches("(?-i)a"));
    }

    /**
     * Константа Pattern.CASE_INSENSITIVE.
     */
    @Test
    public void magicConstant() {
        assertTrue(Pattern.compile("a", Pattern.CASE_INSENSITIVE).matcher("A").matches());
        assertFalse(Pattern.compile("a").matcher("A").matches());
    }
}
