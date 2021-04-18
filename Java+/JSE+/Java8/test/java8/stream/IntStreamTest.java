package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class IntStreamTest {

    @Test
    public void toList() {
        var list = IntStream.of(1, 2, 3)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list, contains(1, 2, 3));
    }

}
