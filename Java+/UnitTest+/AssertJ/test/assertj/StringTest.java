package assertj;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * See also https://github.com/joel-costigliola/assertj-examples/blob/master/assertions-examples/src/test/java/org/assertj/examples/StringAssertionsExamples.java
 */
public class StringTest {
    @Test
    public void string() {
        assertThat("a")
                .isEqualTo("a")
                .isNotEqualTo("b")
                .isIn("a", "b", "c");
    }

    @Test
    public void stringIterableMatchesPattern() {
        Iterable<String> strings = Arrays.asList("a", "b");
        Pattern pattern = Pattern.compile("[ab]");
        strings.forEach(string -> assertThat(string).matches(pattern));
    }
}
