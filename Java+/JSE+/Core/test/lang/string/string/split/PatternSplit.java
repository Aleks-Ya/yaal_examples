package lang.string.string.split;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PatternSplit {

    @Test
    void ok() {
        var p = Pattern.compile("\\s");
        var words = p.split("aa bb cc");
        assertThat(words).containsExactly("aa", "bb", "cc");
    }

    @Test
    void emptyString() {
        var p = Pattern.compile("\\s");
        var words = p.split("");
        assertThat(words).containsExactly("");
    }

    @Test
    void nullString() {
        assertThatThrownBy(() -> Pattern.compile("\\s").split(null)).isInstanceOf(NullPointerException.class);
    }

}