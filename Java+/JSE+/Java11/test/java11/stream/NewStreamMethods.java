package java11.stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewStreamMethods {
    @Test
    public void takeWhile() {
        List<Integer> list = Stream.of(1, 2, 3, 4).takeWhile(value -> value < 3).collect(Collectors.toList());
        assertThat(list, contains(1, 2));
    }

    @Test
    public void dropWhile() {
        List<Integer> list = Stream.of(1, 2, 3, 4).dropWhile(value -> value < 3).collect(Collectors.toList());
        assertThat(list, contains(3, 4));
    }

    @Test
    public void iterate() {
        List<Integer> list = IntStream.iterate(1, operand -> operand < 5, operand -> ++operand).boxed().collect(Collectors.toList());
        assertThat(list, contains(1, 2, 3, 4));
    }
}
