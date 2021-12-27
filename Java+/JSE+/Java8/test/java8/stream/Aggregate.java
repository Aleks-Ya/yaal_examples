package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование аггрегирующих операций.
 */
class Aggregate {
    private final Stream<Integer> stream = Stream.of(1, 2, 3);

    @Test
    void sum() {
        var sum = stream.mapToInt(Integer::intValue).sum();
        assertThat(sum).isEqualTo(6);
    }

    @Test
    void count() {
        var sum = stream.mapToInt(Integer::intValue).count();
        assertThat(sum).isEqualTo(3L);
    }

    @Test
    void average() {
        var average = stream.mapToInt(Integer::intValue).average();
        assertThat(average.getAsDouble()).isEqualTo(2D);
    }

    @Test
    void min() {
        var min = stream.mapToInt(Integer::intValue).min();
        assertThat(min.getAsInt()).isEqualTo(1);
    }

    @Test
    void max() {
        var max = stream.mapToInt(Integer::intValue).max();
        assertThat(max.getAsInt()).isEqualTo(3);
    }
}
