import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Соответствует ли целая строка регулярному выражению.
 */
public class IsMatch {
    private static final String REGEX = "^\\w+\\s*:\\s*\\d+$";
    private static final String SOURCE_MATCH = "width  : 600";
    private static final String SOURCE_NOT_MATCH = "alwaysTooltips: false";

    @Test
    public void realization1() {
        assertTrue(SOURCE_MATCH.matches(REGEX));
        assertFalse(SOURCE_NOT_MATCH.matches(REGEX));
    }

    @Test
    public void realization2() {
        assertTrue(SOURCE_MATCH.matches(REGEX));
        assertFalse(SOURCE_NOT_MATCH.matches(REGEX));
    }
}