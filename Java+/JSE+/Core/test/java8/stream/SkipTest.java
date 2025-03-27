package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Skipping stream elements.
 */
class SkipTest {
    private static final List<Integer> source = List.of(-1, 0, 1, 2);

    @Test
    void skip() {
        var result = source.stream().skip(2).collect(toList());
        assertThat(result).contains(1, 2);
    }

    @Test
    void skipZero() {
        var result = source.stream().skip(0).collect(toList());
        assertThat(result).containsAll(source);
    }

    @Test
    void skipExcess() {
        var result = source.stream().skip(source.size() + 10).collect(toList());
        assertThat(result).isEmpty();
    }

    @Test
    void getLastElement() {
        var result = source.stream().skip(source.size() - 1).collect(toList());
        assertThat(result).contains(2);
    }

}
