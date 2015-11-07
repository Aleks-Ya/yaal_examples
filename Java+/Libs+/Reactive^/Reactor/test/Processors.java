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

    @Test
    public void observe() {
        Streams.just("a", "b")
                .observe(System.out::println)
                .consume();
    }
}
