package stream;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

/**
 * Grouping elements of a List to a Map.
 */
public class GroupingBy {
    private final Stream<Integer> stream = Stream.of(1, 2, 3, -1);

    @Test
    public void sum() {
        String positiveKey = "positive";
        String negativeKey = "negative";

        Map<String, List<Integer>> grouped = stream
                .collect(Collectors.groupingBy(num -> num > 0 ? positiveKey : negativeKey));

        assertThat(grouped, aMapWithSize(2));
        assertThat(grouped.get(positiveKey), contains(1, 2, 3));
        assertThat(grouped.get(negativeKey), contains(-1));
    }
}
