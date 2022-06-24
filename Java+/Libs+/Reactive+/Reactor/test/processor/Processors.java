package processor;

import org.junit.jupiter.api.Test;
import reactor.rx.Streams;

/**
 * Преобразование потоков с помощью обработчиков.
 */
public class Processors {
    @Test
    void filter() {
        Streams.just("a", null, "b")
                .filter(s -> s != null)
                .consume(System.out::println);
    }

    @Test
    void observe() {
        Streams.just("a", "b")
                .observe(System.out::println)
                .consume();
    }

    @Test
    void groupBy() {
        Streams.just("a", "b", "cc", "dd")
                .groupBy(String::length)
                .observe(stream -> stream.consume(s -> System.out.println(stream.key() + " - " + s)))
                .consume();
    }
}
