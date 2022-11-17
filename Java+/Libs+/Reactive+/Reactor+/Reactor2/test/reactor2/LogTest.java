package reactor2;

import org.junit.jupiter.api.Test;
import reactor.rx.Streams;

/**
 * Добавлена зависимость logback-classic.
 */
class LogTest {
    @Test
    void defaultLogger() {
        Streams.just("a", "b")
                .log()
                .consume();
    }

    @Test
    void customLogger() {
        Streams.just(1, 2)
                .log(LogTest.class.getName())
                .consume();
    }
}