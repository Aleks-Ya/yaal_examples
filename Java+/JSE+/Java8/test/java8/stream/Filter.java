package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Фильтрация коллекций.
 */
public class Filter {

    /**
     * Максимально коротка форма.
     */
    @Test
    public void shortForm() {
        var source = Arrays.asList(-1, 0, 1);
        var result = source.stream().filter(t -> t > 0).collect(toList());
        assertThat(result, contains(1));
    }

    /**
     * Максимально подробная форма.
     */
    @Test
    public void longForm() {
        var source = Arrays.asList(-1, 0, 1);

        var stream = source.stream();
        Predicate<Integer> moreThanZeroPredicate = t -> t > 0;
        stream = stream.filter(moreThanZeroPredicate);
        var result = stream.collect(toList());

        assertThat(result, contains(1));
    }
}
