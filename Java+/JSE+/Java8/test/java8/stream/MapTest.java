package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Use {@link Stream#map(Function)}.
 */
class MapTest {

    @Test
    void exceptionInMap() {
        var message = "the_message";
        Function<Integer, Integer> multiplyTwo = num -> {
            if (num == 2) throw new IllegalArgumentException(message);
            return num * 2;
        };
        assertThatThrownBy(() -> {
            var sum = Stream.of(1, 2, 3)
                    .map(multiplyTwo)
                    .reduce(Integer::sum).get();
            assertThat(sum).isEqualTo(12);
        }).isInstanceOf(IllegalArgumentException.class).hasMessage(message);
    }

    @Test
    void modifyKeyAndValues() {
        var map = Map.of("length", 10, "width", 20);
        var actMap = map.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey().toUpperCase(), entry.getValue() * 2))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        var expMap = Map.of("LENGTH", 20, "WIDTH", 40);
        assertThat(actMap).isEqualTo(expMap);
    }

    @Test
    void streamOfMapToMap() {
        var map1 = Map.of("a", 10, "b", 20);
        var map2 = Map.of("y", 100, "z", 200);
        var mapStream = Stream.of(map1, map2);
        var actMap = mapStream.reduce(Map.of(), (m1, m2) -> {
            var newMap = new HashMap<>(m1);
            newMap.putAll(m2);
            return newMap;
        });
        var expMap = Map.of("a", 10, "b", 20, "y", 100, "z", 200);
        assertThat(actMap).isEqualTo(expMap);
    }
}
