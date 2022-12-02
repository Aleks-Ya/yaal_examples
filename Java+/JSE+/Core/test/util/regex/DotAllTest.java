package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Флаг DOTALL: метасимвол . совпадает с line terminators.
 */
class DotAllTest {
    /**
     * Конструкция "(?s)".
     */
    @Test
    void flag() {
        assertThat("a\nb".matches("(?s).*")).isTrue();
        assertThat("a\nb".matches(".*")).isFalse();
    }

    /**
     * Константа Pattern.CASE_INSENSITIVE.
     */
    @Test
    void magicConstant() {
        assertThat(Pattern.compile(".*", Pattern.DOTALL).matcher("a\nb").matches()).isTrue();
        assertThat(Pattern.compile(".*").matcher("a\nb").matches()).isFalse();
    }
}
