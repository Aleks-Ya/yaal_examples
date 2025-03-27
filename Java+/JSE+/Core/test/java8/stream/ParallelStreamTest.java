package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

/**
 * Создание объекта Stream.
 */
class ParallelStreamTest {
    @Test
    void fromCollection() {
        var stream = List.of("a", "b");
        var parallelStream = stream.parallelStream();
    }

    @Test
    void fromStream() {
        var stream = Stream.of("a", "b");
        var parallelStream = stream.parallel();
    }
}
