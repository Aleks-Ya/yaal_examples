package assertj.builtin.collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.INTEGER;
import static util.CollectionUtil.sortedMap;

/**
 * <a href="https://github.com/assertj/assertj-examples/blob/main/assertions-examples/src/test/java/org/assertj/examples/MapAssertionsExamples.java">See also</a>
 */
class MapAssertTest {
    @Test
    void empty() {
        assertThat(Map.of()).isEmpty();
        assertThat(Map.of("a", 1)).isNotEmpty();
    }

    @Test
    void size() {
        assertThat(Map.of()).hasSize(0);
        assertThat(Map.of("a", 1)).hasSize(1);
        assertThat(Map.of("a", 1)).hasSameSizeAs(Map.of("b", 2));
    }

    @Test
    void containsEntry() {
        var map = Map.of("a", 1, "b", 2, "c", 3);
        assertThat(map)
                .contains(Assertions.entry("b", 2), Assertions.entry("a", 1))
                .containsOnly(Assertions.entry("c", 3), Assertions.entry("b", 2), Assertions.entry("a", 1))
                .doesNotContain(Assertions.entry("d", 4));
        assertThat(map)
                .contains(Map.entry("b", 2), Map.entry("a", 1))
                .doesNotContain(Map.entry("d", 4));
        assertThat(map)
                .containsEntry("b", 2)
                .containsEntry("a", 1)
                .doesNotContainEntry("d", 4);
        assertThat(map)
                .containsAllEntriesOf(Map.of("c", 3, "b", 2, "a", 1));
    }

    @Test
    void containsEntryExactly() {
        var map = sortedMap("a", 1, "b", 2, "c", 3);
        assertThat(map)
                .containsExactly(Assertions.entry("a", 1), Assertions.entry("b", 2), Assertions.entry("c", 3))
                .containsExactlyInAnyOrderEntriesOf(Map.of("c", 3, "b", 2, "a", 1))
                .containsExactlyEntriesOf(sortedMap("a", 1, "b", 2, "c", 3));
    }

    @Test
    void containsKey() {
        var map = Map.of("a", 1, "b", 2, "c", 3);
        assertThat(map)
                .containsKeys("b", "a")
                .containsOnlyKeys("c", "b", "a")
                .doesNotContainKeys("d", "e");
        assertThat(map)
                .containsKey("b")
                .containsKey("a")
                .doesNotContainKey("d");
    }

    @Test
    void containsValue() {
        var map = Map.of("a", 1, "b", 2, "c", 3);
        assertThat(map)
                .containsValues(1, 2);
        assertThat(map)
                .containsValue(1)
                .containsValue(2)
                .doesNotContainValue(4);
    }

    @Test
    void extractingByKey() {
        var map = Map.of("a", 1, "b", 2, "c", 3);
        assertThat(map).extractingByKey("b").isEqualTo(2);
        assertThat(map).extractingByKey("b", as(INTEGER)).isEqualTo(2);
        assertThat(map).extractingByKeys("b", "c").containsExactly(2, 3);
    }
}
