package reactor2.broadcaster;

import org.junit.jupiter.api.Test;
import reactor.rx.broadcast.Broadcaster;

/**
 * Использование Hot Streams (Broadcasters).
 */
class BroadcastersTest {
    @Test
    void helloWorld() {
        Broadcaster<String> sink = Broadcaster.create();
        var stream = sink.map(String::toUpperCase);
        stream.consume(System.out::println);
        sink.onNext("Hello World!");
        sink.onNext("Buy World!");
    }
}
