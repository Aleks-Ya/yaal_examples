package reactor2;

import org.junit.jupiter.api.Test;
import reactor.fn.tuple.Tuple;
import reactor.fn.tuple.Tuple2;
import reactor.rx.broadcast.Broadcaster;

import java.time.Instant;

/**
 * Передача нескольких аргументов с помощью кортежа.
 */
class TuplesTest {
    @Test
    void helloWorld() {
        Broadcaster<Tuple2<Instant, String>> sink = Broadcaster.create();
        sink.consume(tuple -> System.out.printf("%s - %s%n", tuple.getT1(), tuple.getT2()));
        sink.onNext(Tuple.of(Instant.now(), "Hi"));
        sink.onNext(Tuple.of(Instant.now(), "Buy"));
        sink.onComplete();
    }
}
