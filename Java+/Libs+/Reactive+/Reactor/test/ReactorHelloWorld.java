import org.junit.jupiter.api.Test;
import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

public class ReactorHelloWorld {
    @Test
    void test() throws InterruptedException {
        Environment.initialize();

        Broadcaster<String> sink = Broadcaster.create(Environment.get());

        sink.dispatchOn(Environment.cachedDispatcher())
                .map(String::toUpperCase)
                .consume(s -> System.out.printf("s=%s%n", s));

        sink.onNext("Hello World!");

        Thread.sleep(500);
    }
}