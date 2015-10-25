import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Поиск без учета регистра.
 */
public class CaseInsensitive {

    /**
     * Конструкция "(?i)".
     */
    @Test
    public void flag() {
        assertTrue(Pattern.matches("(?i)a", "A"));
        assertTrue(Pattern.matches("(?i)\u0064", "\u0044"));
        assertFalse(Pattern.matches("(?-i)a", "A"));
    }

    /**
     * Константа Pattern.CASE_INSENSITIVE.
     */
    @Test
    public void magicConstant() {
        assertTrue(Pattern.compile("a", Pattern.CASE_INSENSITIVE).matcher("A").matches());
    }
}
