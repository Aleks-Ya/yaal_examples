package util.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NewStreamMethodsTest {
    @Test
    void takeWhile() {
        var list = Stream.of(1, 2, 3, 4).takeWhile(value -> value < 3).toList();
        assertThat(list).containsExactly(1, 2);
    }

    @Test
    void dropWhile() {
        var list = Stream.of(1, 2, 3, 4).dropWhile(value -> value < 3).toList();
        assertThat(list).containsExactly(3, 4);
    }

    @Test
    void iterate() {
        var list = IntStream.iterate(1, operand -> operand < 5, operand -> ++operand).boxed().toList();
        assertThat(list).containsExactly(1, 2, 3, 4);
    }
}
