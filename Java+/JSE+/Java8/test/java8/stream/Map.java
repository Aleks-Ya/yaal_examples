package java8.stream;

import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThrows;

/**
 * Use {@link Stream#map(Function)}.
 */
public class Map {

    @Test
    public void exceptionInMap() {
        String message = "the_message";
        Function<Integer, Integer> multiplyTwo = num -> {
            if (num == 2) throw new IllegalArgumentException(message);
            return num * 2;
        };
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            Integer sum = Stream.of(1, 2, 3)
                    .map(multiplyTwo)
                    .reduce(Integer::sum).get();
            assertThat(sum, equalTo(12));
        });
        assertThat(e.getMessage(), equalTo(message));
    }
}
