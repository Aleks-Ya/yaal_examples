package stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Аггрегирующая операция Stream#reduce.
 */
public class CollectorTest {
    private final List<Integer> list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    /**
     * Сумма.
     */
    @Test
    public void sum() {
        Stream<String> stream = Stream.of("a", "b");
        Collector<String, ?, StringBuilder> collector = Collector.of(
        );
        stream.collect(collector);
    }
}
