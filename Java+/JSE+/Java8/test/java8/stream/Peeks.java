package java8.stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Использование Stream#peek.
 */
public class Peeks {

    @Test
    public void peek() {
        StringBuilder sb = new StringBuilder();
        List<String> list = Stream.of("a", "b", "c")
                .peek(sb::append)
                .collect(Collectors.toList());//peek() doesn't work without a terminal operation
        assertThat(list, hasSize(3));
        assertThat(sb.toString(), equalTo("abc"));
    }
}
