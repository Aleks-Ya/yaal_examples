import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

/**
 * Соответствует ли целая строка регулярному выражению.
 */
public class IsMatch {
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