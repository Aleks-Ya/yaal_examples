package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Замена вхождения регулярного выражения в строку подстрокой.
 */
public class SubstringReplaceTest {
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

    @Test
    public void doubleDollar() {
        assertThat("$$".replaceAll("\\$", "A"), equalTo("AA"));
        assertThat("$$".replaceAll("\\${2}", "A"), equalTo("A"));
        assertThat("$$".replaceAll("\\${2}", "\\$"), equalTo("$"));
    }

    @Test
    public void replaceMultipleSpacesWithOne() {
        assertThat("   a   bc    d e ".replaceAll("\\s{2,}", " "), equalTo(" a bc d e "));
    }
}