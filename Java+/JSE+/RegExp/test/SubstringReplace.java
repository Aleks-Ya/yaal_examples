import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Замена вхождения регулярного выражения в строку подстрокой.
 */
public class SubstringReplace {
    private static final String SOURCE = "concert Einaudi ludovico einaudi today ";
    private static final String REGEX = "[Ee]inaudi";
    private static final String REPLACER = "Tankian";

    @Test
    public void realization1() {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(SOURCE);
        assertEquals("concert Tankian ludovico Tankian today ", m.replaceAll(REPLACER));
        assertEquals("concert Tankian ludovico Tankian today ", Pattern.compile(REGEX).matcher(SOURCE).replaceAll(REPLACER));
    }

    @Test
    public void realization2() {
        assertEquals("concert Tankian ludovico einaudi today ", SOURCE.replaceFirst(REGEX, REPLACER));
        assertEquals("concert Tankian ludovico Tankian today ", SOURCE.replaceAll(REGEX, REPLACER));
    }
}