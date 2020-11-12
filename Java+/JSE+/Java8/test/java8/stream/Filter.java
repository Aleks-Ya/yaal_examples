package java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
        List<Integer> source = Arrays.asList(-1, 0, 1);

        List<Integer> result = source.stream().filter(t -> t > 0).collect(toList());

        assertThat(result, contains(1));
    }

    /**
     * Максимально подробная форма.
     */
    @Test
    public void longForm() {
        List<Integer> source = Arrays.asList(-1, 0, 1);

        Stream<Integer> stream = source.stream();
        Predicate<Integer> moreThanZeroPredicate = t -> t > 0;
        stream = stream.filter(moreThanZeroPredicate);
        List<Integer> result = stream.collect(toList());

        assertThat(result, contains(1));
    }
}
