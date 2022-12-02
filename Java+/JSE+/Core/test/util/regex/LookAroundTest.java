package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Примеры позиционной проверки.
 */
class LookAroundTest {
    private static final String NO = "obsolete obsolete";
    private static final String BEGIN = "hede obsolete";
    private static final String MIDDLE = "obsolete hede obsolete";
    private static final String END = "obsolete hede";

    @Test
    void notEndWith() {
        var p = Pattern.compile("^.*(?<!hede)$");
        assertThat(p.matcher(NO).matches()).isTrue();
        assertThat(p.matcher(BEGIN).matches()).isTrue();
        assertThat(p.matcher(MIDDLE).matches()).isTrue();
        assertThat(p.matcher(END).matches()).isFalse();
    }

    @Test
    void endWith() {
        var p = Pattern.compile(".*(?<=hede$)");
        assertThat(p.matcher(NO).matches()).isFalse();
        assertThat(p.matcher(BEGIN).matches()).isFalse();
        assertThat(p.matcher(MIDDLE).matches()).isFalse();
        assertThat(p.matcher(END).matches()).isTrue();
    }

    @Test
    void notContain() {
        var p = Pattern.compile("^((?!hede).)*$");
        assertThat(p.matcher(NO).matches()).isTrue();
        assertThat(p.matcher(BEGIN).matches()).isFalse();
        assertThat(p.matcher(MIDDLE).matches()).isFalse();
        assertThat(p.matcher(END).matches()).isFalse();
    }

    @Test
    void notStartWith() {
        var p = Pattern.compile("^(?!hede).*$");
        assertThat(p.matcher(NO).matches()).isTrue();
        assertThat(p.matcher(BEGIN).matches()).isFalse();
        assertThat(p.matcher(MIDDLE).matches()).isTrue();
        assertThat(p.matcher(END).matches()).isTrue();
    }
}