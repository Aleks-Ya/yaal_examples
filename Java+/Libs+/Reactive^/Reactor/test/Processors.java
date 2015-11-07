import org.junit.Test;
import reactor.rx.Streams;

/**
 * Преобразование потоков с помощью обработчиков.
 */
public class Processors {
    @Test
    public void filter() {
        Streams.just("a", null, "b")
                .filter(s -> s != null)
                .consume(System.out::println);
    }
}
