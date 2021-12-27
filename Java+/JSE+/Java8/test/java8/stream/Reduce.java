package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Аггрегирующая операция Stream#reduce.
 */
class Reduce {
    private static final Stream<Integer> stream = Stream.of(1, 2, 3);

    /**
     * Сумма.
     */
    @Test
    void sum() {
        var sum = stream
                .mapToInt(Integer::intValue)
                .reduce(0, (left, right) -> left + right);
        assertThat(sum).isEqualTo(6);
    }
}
