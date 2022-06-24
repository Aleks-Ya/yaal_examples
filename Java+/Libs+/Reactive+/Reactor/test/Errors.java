import org.junit.jupiter.api.Test;
import reactor.rx.Streams;
import reactor.rx.broadcast.Broadcaster;

import java.io.IOException;

/**
 * Преобразование потоков с помощью обработчиков.
 */
public class Errors {
    @Test
    void onErrorResumeNext() {
        Broadcaster<String> broadcaster = Broadcaster.create();
        broadcaster.onErrorResumeNext(Streams.just("Alternative Message"))
                .consume(System.out::println);
        broadcaster.onNext("test2");
        broadcaster.onError(new RuntimeException());
    }

    @Test
    void onErrorResumeNext2() {
        Broadcaster<String> broadcaster = Broadcaster.create();
        broadcaster
                .onErrorReturn(throwable -> "aaaa")
                .consume(System.out::println);
        broadcaster.onNext("test2");
        broadcaster.onError(new RuntimeException());
    }

    @Test
    void retry() {
        Broadcaster<String> broadcaster = Broadcaster.create();
        broadcaster
                .retry()
                .consume(System.out::println);
        broadcaster.onNext("test1");
        broadcaster.onError(new RuntimeException());
        broadcaster.onNext("test2");
    }

    @Test
    void when() {
        Broadcaster<String> broadcaster = Broadcaster.create();
        broadcaster
                .when(IllegalArgumentException.class, e -> System.out.println("argument " + e.getClass().getSimpleName()))
                .when(IllegalStateException.class, e -> System.out.println("state " + e.getClass().getSimpleName()))
                .when(Exception.class, e -> System.out.println("exception " + e.getClass().getSimpleName()))
                .retry()
                .consume(System.out::println);
        broadcaster.onNext("test1");
        broadcaster.onError(new IllegalArgumentException());
        broadcaster.onError(new IllegalStateException());
        broadcaster.onError(new IOException());
        broadcaster.onNext("test2");
    }
}
