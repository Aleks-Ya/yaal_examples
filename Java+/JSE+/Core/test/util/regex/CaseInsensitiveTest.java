package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Поиск без учета регистра.
 */
class CaseInsensitiveTest {

    /**
     * Конструкция "(?i)".
     */
    @Test
    void flag() {
        assertThat("A".matches("(?i)a")).isTrue();
        assertThat("\u0044".matches("(?i)\u0064")).isTrue();
        assertThat("A".matches("(?-i)a")).isFalse();
    }

    /**
     * Константа Pattern.CASE_INSENSITIVE.
     */
    @Test
    void magicConstant() {
        assertThat(Pattern.compile("a", Pattern.CASE_INSENSITIVE).matcher("A").matches()).isTrue();
        assertThat(Pattern.compile("a").matcher("A").matches()).isFalse();
    }
}
