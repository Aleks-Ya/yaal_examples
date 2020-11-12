package java8.stream;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Использование Stream#peek.
 */
public class Peeks {

    @Test
    public void peek() {
        StringBuilder sb = new StringBuilder();
        Stream.of("a", "b", "c")
                .peek(sb::append)
                .count();//без терминальной операции peek не работает
        assertEquals(sb.toString(), "abc");
    }
}
