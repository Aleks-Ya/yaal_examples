package stream;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Использование Stream#peek.
 */
public class Peeks {

    @Test
    public void peek() {
        Stream<String> stream = Stream.of("a", "b", "c");
        StringBuilder sb = new StringBuilder();
        stream.peek(sb::append).count();//без терминальной операции peek не работает
        assertEquals(sb.toString(), "abc");
    }
}
