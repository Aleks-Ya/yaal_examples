package stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class IntStreamTest {

    @Test
    public void toList() {
        List<Integer> list = IntStream.of(1, 2, 3)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list, contains(1, 2, 3));
    }

}
