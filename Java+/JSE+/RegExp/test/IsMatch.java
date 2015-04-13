import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern p = Pattern.compile(REGEX);
        Matcher m1 = p.matcher(SOURCE_MATCH);
        assertTrue(m1.matches());
        Matcher m2 = p.matcher(SOURCE_NOT_MATCH);
        assertFalse(m2.matches());
    }

    @Test
    public void realization2() {
        assertTrue(Pattern.matches(REGEX, SOURCE_MATCH));
        assertFalse(Pattern.matches(REGEX, SOURCE_NOT_MATCH));
    }
}