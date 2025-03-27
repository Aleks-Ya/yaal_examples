package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.frequency;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Find duplicate elements in a stream.
 */
class FindDuplicatesTest {

    @Test
    void useGroupingBy1() {
        var duplicates = Stream.of(1, 2, 3, 2, 4, 5, 4)
                .collect(groupingBy(num -> num))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .toList();
        assertThat(duplicates).containsOnly(2, 4);
    }

    @Test
    void useGroupingBy2() {
        var duplicates = Stream.of(1, 2, 3, 2, 4, 5, 4)
                .collect(groupingBy(identity(), counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        assertThat(duplicates).containsOnly(2, 4);
    }

    @Test
    void useFrequency() {
        var numbers = List.of(1, 2, 3, 2, 4, 5, 4);
        var duplicates = numbers.stream()
                .filter(i -> frequency(numbers, i) > 1)
                .toList();
        assertThat(duplicates).containsOnly(2, 4);
    }

}
