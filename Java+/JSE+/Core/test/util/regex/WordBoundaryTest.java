package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class WordBoundaryTest {
    private static final String SUBSTRING = "yyy";
    private static final Pattern PATTERN = Pattern.compile("\\byyy");

    @Test
    void stringBeginning() {
        var source = "yyy aaa";
        var m = PATTERN.matcher(source);
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo(SUBSTRING);
        assertThat(m.find()).isFalse();
    }

    @Test
    void wordBeginning() {
        var source = "aaa yyy bbbyyy ccc";
        var m = PATTERN.matcher(source);
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo(SUBSTRING);
        assertThat(m.find()).isFalse();
    }

    @Test
    void dash() {
        var source = "aaa x-yyy bbb";
        var m = PATTERN.matcher(source);
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo(SUBSTRING);
        assertThat(m.find()).isFalse();
    }
}