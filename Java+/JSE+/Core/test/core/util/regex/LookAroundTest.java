package core.util.regex;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Примеры позиционной проверки.
 */
public class LookAroundTest {
    private static final String NO = "obsolete obsolete";
    private static final String BEGIN = "hede obsolete";
    private static final String MIDDLE = "obsolete hede obsolete";
    private static final String END = "obsolete hede";

    @Test
    public void notEndWith() {
        Pattern p = Pattern.compile("^.*(?<!hede)$");
        assertTrue(p.matcher(NO).matches());
        assertTrue(p.matcher(BEGIN).matches());
        assertTrue(p.matcher(MIDDLE).matches());
        assertFalse(p.matcher(END).matches());
    }

    @Test
    public void endWith() {
        Pattern p = Pattern.compile(".*(?<=hede$)");
        assertFalse(p.matcher(NO).matches());
        assertFalse(p.matcher(BEGIN).matches());
        assertFalse(p.matcher(MIDDLE).matches());
        assertTrue(p.matcher(END).matches());
    }

    @Test
    public void notContain() {
        Pattern p = Pattern.compile("^((?!hede).)*$");
        assertTrue(p.matcher(NO).matches());
        assertFalse(p.matcher(BEGIN).matches());
        assertFalse(p.matcher(MIDDLE).matches());
        assertFalse(p.matcher(END).matches());
    }

    @Test
    public void notStartWith() {
        Pattern p = Pattern.compile("^(?!hede).*$");
        assertTrue(p.matcher(NO).matches());
        assertFalse(p.matcher(BEGIN).matches());
        assertTrue(p.matcher(MIDDLE).matches());
        assertTrue(p.matcher(END).matches());
    }
}