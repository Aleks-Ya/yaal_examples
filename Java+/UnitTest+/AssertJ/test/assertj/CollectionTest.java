package assertj;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * See also https://github.com/joel-costigliola/assertj-examples/blob/master/assertions-examples/src/test/java/org/assertj/examples/IterableAssertionsExamples.java
 */
class CollectionTest {
    private static final List<String> list = asList("a", "b", "c");

    @Test
    void chain() {
        assertThat(list).hasSize(3).contains("b").doesNotContain("z");
    }

    @Test
    void filteredOn() {
        assertThat(list)
                .filteredOn(s -> !s.equals("b"))
                .containsOnly("a", "c");
    }

    @Test
    void elementByIndex() {
        assertThat(list).element(1).isEqualTo("b");
    }
}
