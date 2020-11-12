package java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Создание объекта Stream.
 */
public class ParallelStream {
    @Test
    public void fromCollection() {
        List<String> stream = Arrays.asList("a", "b");
        Stream<String> parallelStream = stream.parallelStream();
    }

    @Test
    public void fromStream() {
        Stream<String> stream = Stream.of("a", "b");
        Stream<String> parallelStream = stream.parallel();
    }
}
