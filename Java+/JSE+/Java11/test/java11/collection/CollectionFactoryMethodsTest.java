package java11.collection;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionFactoryMethodsTest {
    @Test
    void list() {
        var list = List.of(1, 2, 3);
        assertThat(list).containsExactly(1, 2, 3);
    }

    @Test
    void set() {
        var set = Set.of(1, 2, 3);
        assertThat(set).containsExactlyInAnyOrder(1, 2, 3);
    }

    @Test
    void map() {
        var map = Map.of("age", 30, "number", 1);
        assertThat(map).containsEntry("age", 30).containsEntry("number", 1);
    }
}
