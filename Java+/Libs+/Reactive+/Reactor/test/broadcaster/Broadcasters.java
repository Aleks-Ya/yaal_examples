package broadcaster;

import org.junit.jupiter.api.Test;
import reactor.rx.Stream;
import reactor.rx.broadcast.Broadcaster;

/**
 * Использование Hot Streams (Broadcasters).
 */
public class Broadcasters {
    @Test
    void helloWorld() {
        Broadcaster<String> sink = Broadcaster.create();
        Stream<String> stream = sink.map(String::toUpperCase);
        stream.consume(System.out::println);

        sink.onNext("Hello World!");
        sink.onNext("Buy World!");
    }
}
