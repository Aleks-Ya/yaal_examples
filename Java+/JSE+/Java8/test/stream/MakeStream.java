package stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Создание объекта Stream.
 */
public class MakeStream {
    @Test
    public void test() {
        Stream<Object> empty = Stream.empty();
        Stream<String> stream = Stream.of("a", "b");
        IntStream range = IntStream.range(1, 5);
        DoubleStream array = Arrays.stream(new double[]{1, 2, 4});
    }
}
