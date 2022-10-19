package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class IntStreamTest {

    @Test
    void toList() {
        var list1 = IntStream.of(1, 2, 3).boxed().collect(Collectors.toList());
        assertThat(list1).contains(1, 2, 3);
        var list2 = IntStream.of(1, 2, 3).boxed().toList();
        assertThat(list2).contains(1, 2, 3);
    }

    @Test
    void fromZeroToN() {
        var list = IntStream.range(0, 3).boxed().toList();
        assertThat(list).containsExactly(0, 1, 2);
    }

}
