import org.junit.jupiter.api.Test;
import reactor.rx.Streams;

/**
 * Добавлена зависимость logback-classic.
 */
public class Log {
    @Test
    void defaultLogger() {
        Streams.just("a", "b")
                .log()
                .consume();
    }

    @Test
    void customLogger() {
        Streams.just(1, 2)
                .log(Log.class.getName())
                .consume();
    }
}