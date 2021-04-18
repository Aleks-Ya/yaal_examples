package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThrows;

/**
 * Use {@link Stream#map(Function)}.
 */
public class MapTest {

    @Test
    public void exceptionInMap() {
        var message = "the_message";
        Function<Integer, Integer> multiplyTwo = num -> {
            if (num == 2) throw new IllegalArgumentException(message);
            return num * 2;
        };
        var e = assertThrows(IllegalArgumentException.class, () -> {
            var sum = Stream.of(1, 2, 3)
                    .map(multiplyTwo)
                    .reduce(Integer::sum).get();
            assertThat(sum, equalTo(12));
        });
        assertThat(e.getMessage(), equalTo(message));
    }

    @Test
    public void modifyKeyAndValues() {
        var map = Map.of("length", 10, "width", 20);
        var actMap = map.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey().toUpperCase(), entry.getValue() * 2))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        var expMap = Map.of("LENGTH", 20, "WIDTH", 40);
        assertThat(actMap, equalTo(expMap));
    }
}
