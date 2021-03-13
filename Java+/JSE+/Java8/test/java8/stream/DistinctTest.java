package java8.stream;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

/**
 * Удаление дубликатов с помощью distinct().
 */
public class DistinctTest {
    @Test
    public void test() {
        var act = Stream.of("a", "b", "a")
                .distinct()
                .collect(Collectors.toList());
        assertThat(act, hasSize(2));
        assertThat(act, contains("a", "b"));
    }
}
