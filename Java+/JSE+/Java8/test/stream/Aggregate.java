package stream;

import org.junit.Test;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Использование аггрегирующих операций.
 */
public class Aggregate {
    private final Stream<Integer> stream = Stream.of(1, 2, 3);

    @Test
    public void sum() {
        int sum = stream.mapToInt(Integer::intValue).sum();
        assertThat(sum, equalTo(6));
    }

    @Test
    public void count() {
        long sum = stream.mapToInt(Integer::intValue).count();
        assertThat(sum, equalTo(3L));
    }

    @Test
    public void average() {
        OptionalDouble average = stream.mapToInt(Integer::intValue).average();
        assertThat(average.getAsDouble(), equalTo(2D));
    }

    @Test
    public void min() {
        OptionalInt min = stream.mapToInt(Integer::intValue).min();
        assertThat(min.getAsInt(), equalTo(1));
    }

    @Test
    public void max() {
        OptionalInt max = stream.mapToInt(Integer::intValue).max();
        assertThat(max.getAsInt(), equalTo(3));
    }
}
