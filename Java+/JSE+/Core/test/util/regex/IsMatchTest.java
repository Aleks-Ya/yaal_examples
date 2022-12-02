package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Соответствует ли целая строка регулярному выражению.
 */
class IsMatchTest {
    private static final String REGEX = "^\\w+\\s*:\\s*\\d+$";
    private static final String SOURCE_MATCH = "width  : 600";
    private static final String SOURCE_NOT_MATCH = "alwaysTooltips: false";

    @Test
    void string() {
        assertThat(SOURCE_MATCH.matches(REGEX)).isTrue();
        assertThat(SOURCE_NOT_MATCH.matches(REGEX)).isFalse();
    }

    @Test
    void pattern() {
        var pattern = Pattern.compile(REGEX);
        assertThat(pattern.matcher(SOURCE_MATCH).matches()).isTrue();
        assertThat(pattern.matcher(SOURCE_NOT_MATCH).matches()).isFalse();
    }
}