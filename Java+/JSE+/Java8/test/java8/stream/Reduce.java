package java8.stream;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Аггрегирующая операция Stream#reduce.
 */
public class Reduce {
    private static final Stream<Integer> stream = Stream.of(1, 2, 3);

    /**
     * Сумма.
     */
    @Test
    public void sum() {
        var sum = stream
                .mapToInt(Integer::intValue)
                .reduce(0, (left, right) -> left + right);
        assertThat(sum, equalTo(6));
    }
}
