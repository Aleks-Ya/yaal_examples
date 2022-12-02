package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Поиск нескольких вхождений регулярного выражения в строку.
 */
class SubstringSearchTest {

    @Test
    void main() {
        var source = "concert Einaudi ludovico einaudi today ";
        var p = Pattern.compile("[Ee]inaudi");
        var m = p.matcher(source);
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("Einaudi");
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("einaudi");
        assertThat(m.find()).isFalse();
    }
}