package stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Удаление дубликатов с помощью distinct().
 */
public class DistinctTest {
    @Test
    public void test() {
        Stream<String> stream = Stream.of("a", "b", "a");

        List<String> act = stream.distinct().collect(Collectors.toList());

        assertThat(act, hasSize(2));
        assertThat(act, contains("a", "b"));
    }
}
