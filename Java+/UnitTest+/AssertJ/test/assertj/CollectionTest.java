package assertj;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * See also https://github.com/joel-costigliola/assertj-examples/blob/master/assertions-examples/src/test/java/org/assertj/examples/IterableAssertionsExamples.java
 */
class CollectionTest {

    @Test
    void chain() {
        assertThat(asList("a", "b", "c")).hasSize(3).contains("b").doesNotContain("z");
    }

    @Test
    void filteredOn() {
        assertThat(asList("a", "bb", "c"))
                .filteredOn(s -> s.length() == 1)
                .containsOnly("a", "c");
    }
}
