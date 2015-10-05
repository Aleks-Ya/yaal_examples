package stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Использование аггрегирующих операций.
 */
public class Aggregate {
    private final List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void sum() {
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertThat(sum, equalTo(6));
    }

    @Test
    public void count() {
        long sum = list.stream().mapToInt(Integer::intValue).count();
        assertThat(sum, equalTo(3));
    }

    @Test
    public void average() {
        OptionalDouble average = list.stream().mapToInt(Integer::intValue).average();
        assertThat(average.getAsDouble(), equalTo(2D));
    }

    @Test
    public void min() {
        OptionalInt min = list.stream().mapToInt(Integer::intValue).min();
        assertThat(min.getAsInt(), equalTo(1));
    }

    @Test
    public void max() {
        OptionalInt max = list.stream().mapToInt(Integer::intValue).max();
        assertThat(max.getAsInt(), equalTo(3));
    }
}
