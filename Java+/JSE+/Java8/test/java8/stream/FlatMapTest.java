package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование Stream#flatMap.
 */
class FlatMapTest {

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

    @Test
    void listOfArrays() {
        var arr1 = new String[]{"a", "b"};
        var arr2 = new String[]{"c", "d"};
        var arrays = List.of(arr1, arr2);
        var flatList = arrays.stream().flatMap(Arrays::stream).toList();
        assertThat(flatList).containsExactly("a", "b", "c", "d");
    }
}
