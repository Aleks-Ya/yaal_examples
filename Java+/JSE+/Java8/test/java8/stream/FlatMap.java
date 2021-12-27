package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование Stream#flatMap.
 */
class FlatMap {

    @Test
    void listOfListsToList() {
        var list1 = List.of("a", "b");
        var list2 = List.of("c", "d");
        var count = Stream.of(list1, list2)
                .flatMap(Collection::stream)
                .peek(System.out::println)
                .count();
        assertThat(count).isEqualTo(4L);
    }
}
