package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class SortTest {

    @Test
    void reverse() {
        var result = Stream.of(-1, 0, 1)
                .sorted(Comparator.reverseOrder())
                .collect(toList());

        assertThat(result).contains(1, 0, -1);
    }
}
