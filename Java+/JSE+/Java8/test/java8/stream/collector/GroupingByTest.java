package java8.stream.collector;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.List.of;
import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Grouping elements of a List to a Map.
 */
class GroupingByTest {

    @Test
    void groupBy() {
        var stream = Stream.of(1, 2, 3, -1);
        var positiveKey = "positive";
        var negativeKey = "negative";

        var grouped = stream
                .collect(groupingBy(num -> num > 0 ? positiveKey : negativeKey));

        assertThat(grouped).hasSize(2)
                .containsEntry(positiveKey, of(1, 2, 3))
                .containsEntry(negativeKey, of(-1));
    }

    /**
     * Keys of outcome map are ordered (used LinkedHashMap).
     */
    @Test
    void groupByOrdered() {
        var stream = Stream.of(1, 2, 0, 3, -1);
        var positiveKey = "positive";
        var zeroKey = "zero";
        var negativeKey = "negative";

        Map<String, List<Integer>> grouped = stream
                .collect(groupingBy(
                        num -> {
                            if (num == 0) {
                                return zeroKey;
                            }
                            return num > 0 ? positiveKey : negativeKey;
                        },
                        LinkedHashMap::new,
                        Collectors.toList()));

        assertThat(grouped).hasSize(3).containsExactlyInAnyOrderEntriesOf(Map.of(
                positiveKey, of(1, 2, 3),
                zeroKey, of(0),
                negativeKey, of(-1)));
    }

}
