package java8.stream;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Использование аггрегирующих операций.
 */
public class Aggregate {
    private final Stream<Integer> stream = Stream.of(1, 2, 3);

    @Test
    public void sum() {
        var sum = stream.mapToInt(Integer::intValue).sum();
        assertThat(sum, equalTo(6));
    }

    @Test
    public void count() {
        var sum = stream.mapToInt(Integer::intValue).count();
        assertThat(sum, equalTo(3L));
    }

    @Test
    public void average() {
        var average = stream.mapToInt(Integer::intValue).average();
        assertThat(average.getAsDouble(), equalTo(2D));
    }

    @Test
    public void min() {
        var min = stream.mapToInt(Integer::intValue).min();
        assertThat(min.getAsInt(), equalTo(1));
    }

    @Test
    public void max() {
        var max = stream.mapToInt(Integer::intValue).max();
        assertThat(max.getAsInt(), equalTo(3));
    }
}
