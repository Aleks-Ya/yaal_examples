package assertj;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * See also https://github.com/joel-costigliola/assertj-examples/blob/master/assertions-examples/src/test/java/org/assertj/examples/IterableAssertionsExamples.java
 */
class CollectionAssertTest {
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

    @Test
    void singleElement() {
        assertThat(List.of("a")).singleElement().isInstanceOf(String.class);
    }

    @Test
    void allMatch() {
        assertThat(list).allMatch(element -> element.length() == 1);
    }

    @Test
    void allSatisfy() {
        assertThat(list).allSatisfy(element -> assertThat(element.length()).isEqualTo(1));
    }

    @Test
    void genericsWildcard1() {
        var e1 = new RuntimeException();
        var e2 = new IOException();
        List<? extends Exception> list = asList(e1, e2);
        assertThat(list).asList().contains(e1, e2);
    }

    @Test
    void genericsWildcard2() {
        var e1 = new RuntimeException();
        var e2 = new IOException();
        List<? extends Exception> list = asList(e1, e2);
        //noinspection unchecked,CastCanBeRemovedNarrowingVariableType
        assertThat((List<Exception>) list).contains(e1, e2);
    }

    @Test
    void untyped() {
        List list = asList("a", "b", "c");
        assertThat(list).asList().contains("a", "b", "c");
        assertThat(list).asInstanceOf(InstanceOfAssertFactories.list(String.class)).asList().contains("a", "b", "c");
    }

}
