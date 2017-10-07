package stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

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
}
