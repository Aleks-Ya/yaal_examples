package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование Stream#peek.
 */
class Peeks {

    @Test
    void peek() {
        var sb = new StringBuilder();
        var list = Stream.of("a", "b", "c")
                .peek(sb::append)
                .collect(Collectors.toList());//peek() doesn't work without a terminal operation
        assertThat(list).hasSize(3);
        assertThat(sb.toString()).isEqualTo("abc");
    }
}
