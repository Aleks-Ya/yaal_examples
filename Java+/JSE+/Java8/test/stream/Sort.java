package stream;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class Sort {

    @Test
    public void reverse() {
        List<Integer> result = Stream.of(-1, 0, 1)
                .sorted(Comparator.reverseOrder())
                .collect(toList());

        assertThat(result, contains(1, 0, -1));
    }
}
