package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(m.replaceAll(REPLACER)).isEqualTo("concert Tankian ludovico Tankian today ");
        assertThat(Pattern.compile(REGEX).matcher(SOURCE).replaceAll(REPLACER)).isEqualTo("concert Tankian ludovico Tankian today ");
    }

    @Test
    void realization2() {
        assertThat(SOURCE.replaceFirst(REGEX, REPLACER)).isEqualTo("concert Tankian ludovico einaudi today ");
        assertThat(SOURCE.replaceAll(REGEX, REPLACER)).isEqualTo("concert Tankian ludovico Tankian today ");
    }

    @Test
    void doubleDollar() {
        assertThat("$$".replaceAll("\\$", "A")).isEqualTo("AA");
        assertThat("$$".replaceAll("\\${2}", "A")).isEqualTo("A");
        assertThat("$$".replaceAll("\\${2}", "\\$")).isEqualTo("$");
    }

    @Test
    void replaceMultipleSpacesWithOne() {
        assertThat("   a   bc    d e ".replaceAll("\\s{2,}", " ")).isEqualTo(" a bc d e ");
    }

    @Test
    void replaceGroup() {
        var act = "http://host:8080/data?param1=33&param2=66".replaceAll("(param1=)(\\d+)", "$1\\44");
        assertThat(act).isEqualTo("http://host:8080/data?param1=44&param2=66");

        act = "http://host:8080/data?param1=33min&param2=66".replaceAll("(param1=)(\\d+)(\\w+)", "$1\\44$3");
        assertThat(act).isEqualTo("http://host:8080/data?param1=44min&param2=66");
    }
}