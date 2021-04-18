package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Создание объекта Stream.
 */
public class MakeStream {
    @Test
    public void test() {
        var empty = Stream.empty();
        var stream = Stream.of("a", "b");
        var range = IntStream.range(1, 5);
        var array = Arrays.stream(new double[]{1, 2, 4});
    }

    @Test
    public void iterable() {
        Iterable<String> itr = Arrays.asList("a", "b");
        var stream = StreamSupport.stream(itr.spliterator(), false);
    }
}
