package util.collection.map;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

class SortedKeysTest {
    @Test
    void sort() {
        var map = Map.of("c", 3, "b", 2, "a", 1);
        var treeMap = new TreeMap<>(map);
        assertThat(treeMap.keySet()).isInstanceOf(SortedSet.class);
        assertThat(treeMap.keySet().stream().toList()).containsExactly("a", "b", "c");
    }
}
