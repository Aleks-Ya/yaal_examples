package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class IntStreamTest {

    @Test
    void toList() {
        var list = IntStream.of(1, 2, 3)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).contains(1, 2, 3);
    }

}
