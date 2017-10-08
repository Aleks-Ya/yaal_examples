package core.lang.string.string.split;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

public class PatternSplitTest {

    @Test
    public void ok() {
        Pattern p = Pattern.compile("\\s");
        String[] words = p.split("aa bb cc");
        Assert.assertThat(words, Matchers.arrayContaining("aa", "bb", "cc"));
    }

    @Test
    public void emptyString() {
        Pattern p = Pattern.compile("\\s");
        String[] words = p.split("");
        Assert.assertThat(words, Matchers.arrayContaining(""));
    }

    @Test(expected = NullPointerException.class)
    public void nullString() {
        Pattern p = Pattern.compile("\\s");
        //noinspection ResultOfMethodCallIgnored
        p.split(null);
    }

}