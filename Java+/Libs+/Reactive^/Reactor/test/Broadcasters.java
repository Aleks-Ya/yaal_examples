import org.junit.Test;
import reactor.rx.broadcast.Broadcaster;

/**
 * Использование Hot Streams (Broadcasters).
 */
public class Broadcasters {
    @Test
    public void helloWorld() {
        Broadcaster<String> sink = Broadcaster.create();
        sink.map(String::toUpperCase).consume(System.out::println);

        sink.onNext("Hello World!");
        sink.onNext("Buy World!");
    }
}
