package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Использование Stream#flatMap.
 */
public class FlatMap {

    @Test
    public void listOfListsToList() {
        var list1 = Arrays.asList("a", "b");
        var list2 = Arrays.asList("c", "d");
        var count = Stream.of(list1, list2)
                .flatMap(Collection::stream)
                .peek(System.out::println)
                .count();
        assertThat(count, equalTo(4L));
    }
}
