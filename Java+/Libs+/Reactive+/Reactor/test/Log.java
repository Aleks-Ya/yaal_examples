import org.junit.Test;
import reactor.rx.Streams;

/**
 * Добавлена зависимость logback-classic.
 */
public class Log {
    @Test
    public void defaultLogger() {
        Streams.just("a", "b")
                .log()
                .consume();
    }

    @Test
    public void customLogger() {
        Streams.just(1, 2)
                .log(Log.class.getName())
                .consume();
    }
}