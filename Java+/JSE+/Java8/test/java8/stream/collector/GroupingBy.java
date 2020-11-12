package java8.stream.collector;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.contains;

/**
 * Grouping elements of a List to a Map.
 */
public class GroupingBy {

    @Test
    public void groupBy() {
        Stream<Integer> stream = Stream.of(1, 2, 3, -1);
        String positiveKey = "positive";
        String negativeKey = "negative";

        Map<String, List<Integer>> grouped = stream
                .collect(Collectors.groupingBy(num -> num > 0 ? positiveKey : negativeKey));

        assertThat(grouped, aMapWithSize(2));
        assertThat(grouped.get(positiveKey), contains(1, 2, 3));
        assertThat(grouped.get(negativeKey), contains(-1));
    }


    /**
     * Keys of outcome map are ordered (used LinkedHashMap).
     */
    @Test
    public void groupByOrdered() {
        Stream<Integer> stream = Stream.of(1, 2, 0, 3, -1);
        String positiveKey = "positive";
        String zeroKey = "zero";
        String negativeKey = "negative";

        Map<String, List<Integer>> grouped = stream
                .collect(Collectors.groupingBy(
                        num -> {
                            if (num == 0) {
                                return zeroKey;
                            }
                            return num > 0 ? positiveKey : negativeKey;
                        },
                        LinkedHashMap::new,
                        Collectors.toList()));

        assertThat(grouped, aMapWithSize(3));
        assertThat(grouped.get(positiveKey), contains(1, 2, 3));
        assertThat(grouped.get(zeroKey), contains(0));
        assertThat(grouped.get(negativeKey), contains(-1));
        assertThat(grouped.keySet(), contains(positiveKey, zeroKey, negativeKey));
    }
}
