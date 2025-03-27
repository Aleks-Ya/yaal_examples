package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Создание объекта Stream.
 */
class MakeStreamTest {
    @Test
    void test() {
        var empty = Stream.empty();
        var stream = Stream.of("a", "b");
        var range = IntStream.range(1, 5);
        var array = Arrays.stream(new double[]{1, 2, 4});
    }

    @Test
    void iterable() {
        Iterable<String> itr = List.of("a", "b");
        var stream = StreamSupport.stream(itr.spliterator(), false);
    }
}
