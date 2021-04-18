package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Создание объекта Stream.
 */
public class ParallelStream {
    @Test
    public void fromCollection() {
        var stream = Arrays.asList("a", "b");
        var parallelStream = stream.parallelStream();
    }

    @Test
    public void fromStream() {
        var stream = Stream.of("a", "b");
        var parallelStream = stream.parallel();
    }
}
