package stream;

import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Использование Stream#map.
 */
public class Map {

    @Test
    public void listOfListsToList() {
        Function<Integer, Integer> multiplyTwo = num -> {
            if (num == 2) throw new IllegalArgumentException();
            return num * 2;
        };
        Integer sum = Stream.of(1, 2, 3)
                .map(multiplyTwo)
                .reduce(Integer::sum).get();
        assertThat(sum, equalTo(12));
    }
}
