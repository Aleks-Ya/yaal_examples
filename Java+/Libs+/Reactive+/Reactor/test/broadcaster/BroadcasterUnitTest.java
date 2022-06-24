package broadcaster;

import org.junit.jupiter.api.Test;
import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование Broadcasters.
 */
class BroadcasterUnitTest {

    @Test
    void test() {
        //prepare
        Environment.initialize();
        Broadcaster<String> sink = Broadcaster.create(Environment.newDispatcher()); //run broadcaster in separate thread (dispatcher)
        var sb = new StringBuilder();
        sink
                .observe(s -> sleep(100)) //long-time operation
                .consume(sb::append);

        //do
        sink.onNext("a");
        sink.onNext("b");

        //assert
        sleep(500);//wait while broadcaster finished (if comment this line then the test will fail)
        assertThat(sb.toString()).isEqualTo("ab");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
