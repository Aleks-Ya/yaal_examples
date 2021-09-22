package lang.string.string.split;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PatternSplitTest {

    @Test
    void ok() {
        Pattern p = Pattern.compile("\\s");
        String[] words = p.split("aa bb cc");
        assertThat(words, arrayContaining("aa", "bb", "cc"));
    }

    @Test
    void emptyString() {
        Pattern p = Pattern.compile("\\s");
        String[] words = p.split("");
        assertThat(words, arrayContaining(""));
    }

    @Test
    void nullString() {
        assertThrows(NullPointerException.class, () -> {
            Pattern p = Pattern.compile("\\s");
            //noinspection ResultOfMethodCallIgnored
            p.split(null);
        });
    }

}