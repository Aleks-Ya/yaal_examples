package promise;

import org.junit.jupiter.api.Test;
import reactor.rx.Promise;
import reactor.rx.Stream;
import reactor.rx.Streams;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Преобразование Stream в Promise.
 */
public class StreamToPromise {
    @Test
    public void takeFirstElementOfStream() {
        Stream<String> stream = Streams.just("a", "b");

        Promise<String> promise1 = stream.next();
        String value1 = promise1.get();
        assertThat(value1, equalTo("a"));

        Promise<String> promise2 = stream.next();
        String value2 = promise2.get();
        assertThat(value2, equalTo("a"));//все равно "a"
    }
}
