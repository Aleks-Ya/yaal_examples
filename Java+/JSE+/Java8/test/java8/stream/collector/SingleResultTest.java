package java8.stream.collector;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Из стрима должен остаться один элемент.
 */
class SingleResultTest {
    private final Stream<String> stream = Stream.of("a", "b", "c");

    @Test
    void singleElement() {
        var s = Stream.of("a", "b", "c")
                .filter("b"::equals)
                .collect(new SingleElementCollector<>());
        var single = stream
                .filter("b"::equals)
                .collect(new SingleElementCollector<>());
        assertThat(single).isEqualTo("b");
    }

    @Test
    void noElements() {
        assertThrows(NoSuchElementException.class, () -> stream
                .filter("d"::equals)
                .collect(new SingleElementCollector<>()));
    }

    @Test
    void moreThanOneElement() {
        assertThrows(IllegalStateException.class, () -> stream
                .filter(element -> element.equals("b") || element.equals("c"))
                .collect(new SingleElementCollector<>()));
    }
}
