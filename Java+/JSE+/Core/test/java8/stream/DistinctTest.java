package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Удаление дубликатов с помощью distinct().
 */
class DistinctTest {
    @Test
    void test() {
        var act = Stream.of("a", "b", "a")
                .distinct()
                .collect(Collectors.toList());
        assertThat(act).hasSize(2).contains("a", "b");
    }
}
