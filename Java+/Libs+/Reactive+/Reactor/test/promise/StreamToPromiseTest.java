package promise;

import org.junit.jupiter.api.Test;
import reactor.rx.Streams;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Преобразование Stream в Promise.
 */
class StreamToPromiseTest {
    @Test
    void takeFirstElementOfStream() {
        var stream = Streams.just("a", "b");

        var promise1 = stream.next();
        var value1 = promise1.get();
        assertThat(value1).isEqualTo("a");

        var promise2 = stream.next();
        var value2 = promise2.get();
        assertThat(value2).isEqualTo("a");//все равно "a"
    }
}
