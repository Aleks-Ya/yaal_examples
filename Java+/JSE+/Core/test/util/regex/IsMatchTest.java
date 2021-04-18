package util.regex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

/**
 * Соответствует ли целая строка регулярному выражению.
 */
public class IsMatchTest {
    private static final String REGEX = "^\\w+\\s*:\\s*\\d+$";
    private static final String SOURCE_MATCH = "width  : 600";
    private static final String SOURCE_NOT_MATCH = "alwaysTooltips: false";

    @Test
    public void string() {
        assertTrue(SOURCE_MATCH.matches(REGEX));
        assertFalse(SOURCE_NOT_MATCH.matches(REGEX));
    }

    @Test
    public void pattern() {
    	Pattern pattern = Pattern.compile(REGEX);
    	assertTrue(pattern.matcher(SOURCE_MATCH).matches());
    	assertFalse(pattern.matcher(SOURCE_NOT_MATCH).matches());
    }
}