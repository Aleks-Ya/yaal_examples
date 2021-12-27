package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class Limit {
    private static final List<Integer> source = List.of(-1, 0, 1, 2);

    @Test
    void limit() {
        var result = source.stream().limit(2).collect(toList());
        assertThat(result).contains(-1, 0);
    }

    @Test
    void allExceptLast() {
        assertThat(source.stream().limit(source.size() - 1).collect(toList())).contains(-1, 0, 1);
        assertThat(Stream.of().limit(source.size() - 1).collect(toList())).isEmpty();
        assertThat(Stream.of(3).limit(source.size() - 1).collect(toList())).contains(3);
    }

}
