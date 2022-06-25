package util.collection.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

class MergeMapsTest {
    private static final Map<String, Integer> map1 = Map.of("a", 10, "b", 20);
    private static final Map<String, Integer> map2 = Map.of("c", 100, "a", 200);
    private static final Map<String, Integer> expMerged = Map.of("a", 200, "b", 20, "c", 100);

    @Test
    void putAll() {
        var merged = new HashMap<>(map1);
        merged.putAll(map2);
        assertThat(merged).isEqualTo(expMerged);
    }

    @Test
    void streamConcat() {
        var merged = Stream
                .concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
        assertThat(merged).isEqualTo(expMerged);
    }

    @Test
    void streamOf() {
        var merged = Stream.of(map1, map2)
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
        assertThat(merged).isEqualTo(expMerged);
    }
}
