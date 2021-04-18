package java8.stream.collector;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Из стрима должен остаться один элемент.
 */
public class SingleResultTest {
    private final Stream<String> stream = Stream.of("a", "b", "c");

    @Test
    public void singleElement() {
        var s = Stream.of("a", "b", "c")
                .filter("b"::equals)
                .collect(new SingleElementCollector<>());
        var single = stream
                .filter("b"::equals)
                .collect(new SingleElementCollector<>());
        assertThat(single, equalTo("b"));
    }

    @Test
    public void noElements() {
        assertThrows(NoSuchElementException.class, () -> stream
                .filter("d"::equals)
                .collect(new SingleElementCollector<>()));
    }

    @Test
    public void moreThanOneElement() {
        assertThrows(IllegalStateException.class, () -> stream
                .filter(element -> element.equals("b") || element.equals("c"))
                .collect(new SingleElementCollector<>()));
    }
}
