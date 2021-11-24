package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Замена вхождения регулярного выражения в строку подстрокой.
 */
class SubstringReplaceTest {
    private static final String SOURCE = "concert Einaudi ludovico einaudi today ";
    private static final String REGEX = "[Ee]inaudi";
    private static final String REPLACER = "Tankian";

    @Test
    void realization1() {
        var p = Pattern.compile(REGEX);
        var m = p.matcher(SOURCE);
        assertEquals("concert Tankian ludovico Tankian today ", m.replaceAll(REPLACER));
        assertEquals("concert Tankian ludovico Tankian today ", Pattern.compile(REGEX).matcher(SOURCE).replaceAll(REPLACER));
    }

    @Test
    void realization2() {
        assertEquals("concert Tankian ludovico einaudi today ", SOURCE.replaceFirst(REGEX, REPLACER));
        assertEquals("concert Tankian ludovico Tankian today ", SOURCE.replaceAll(REGEX, REPLACER));
    }

    @Test
    void doubleDollar() {
        assertThat("$$".replaceAll("\\$", "A"), equalTo("AA"));
        assertThat("$$".replaceAll("\\${2}", "A"), equalTo("A"));
        assertThat("$$".replaceAll("\\${2}", "\\$"), equalTo("$"));
    }

    @Test
    void replaceMultipleSpacesWithOne() {
        assertThat("   a   bc    d e ".replaceAll("\\s{2,}", " "), equalTo(" a bc d e "));
    }

    @Test
    void replaceGroup() {
        var act = "http://host:8080/data?param1=33&param2=66".replaceAll("(param1=)(\\d+)", "$1\\44");
        assertThat(act, equalTo("http://host:8080/data?param1=44&param2=66"));

        act = "http://host:8080/data?param1=33min&param2=66".replaceAll("(param1=)(\\d+)(\\w+)", "$1\\44$3");
        assertThat(act, equalTo("http://host:8080/data?param1=44min&param2=66"));
    }
}