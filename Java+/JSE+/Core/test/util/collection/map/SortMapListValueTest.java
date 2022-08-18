package util.collection.map;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sort the value list of a "Map<String, List<String>>".
 */
class SortMapListValueTest {
    @Test
    void sort() {
        var map = Map.of("a", List.of("3", "1", "2"), "b", List.of("7", "8", "6"));
        var sortedMap = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    List<String> valueList = new ArrayList<>(entry.getValue());
                    valueList.sort(String::compareTo);
                    return valueList;
                }));
        assertThat(sortedMap).containsEntry("a", List.of("1", "2", "3")).containsEntry("b", List.of("6", "7", "8"));
    }
}
