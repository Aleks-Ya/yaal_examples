package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Фильтрация коллекций.
 */
class Filter {

    /**
     * Максимально коротка форма.
     */
    @Test
    void shortForm() {
        var source = List.of(-1, 0, 1);
        var result = source.stream().filter(t -> t > 0).collect(toList());
        assertThat(result).contains(1);
    }

    /**
     * Максимально подробная форма.
     */
    @Test
    void longForm() {
        var source = List.of(-1, 0, 1);

        var stream = source.stream();
        Predicate<Integer> moreThanZeroPredicate = t -> t > 0;
        stream = stream.filter(moreThanZeroPredicate);
        var result = stream.collect(toList());

        assertThat(result).contains(1);
    }
}
