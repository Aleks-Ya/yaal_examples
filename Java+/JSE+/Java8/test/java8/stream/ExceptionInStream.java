package java8.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionInStream {

    @Test
    public void runtimeException() {
        assertThrows(RuntimeException.class, () -> {
            var list = Stream.of("a", "bb", null, "cc", null)
                    .filter(s -> s.length() > 1)
                    .collect(Collectors.toList());
            System.out.println(list);
        });
    }

    @Test
    public void checkedException() {
        assertThrows(RuntimeException.class, () -> {
            var list = Stream.of(1, 2, null, 3, null)
                    .map(s -> {
                        try {
                            return throwChecked();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());
            assertThat(list, contains(1, 2, 3));
        });

    }

    private Integer throwChecked() throws Exception {
        throw new Exception();
    }
}
