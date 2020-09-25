package broadcaster;

import org.junit.Test;
import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Тестирование Broadcasters.
 */
public class BroadcasterUnitTest {

    @Test
    public void test() {
        //prepare
        Environment.initialize();
        Broadcaster<String> sink = Broadcaster.create(Environment.newDispatcher()); //run broadcaster in separate thread (dispatcher)
        StringBuilder sb = new StringBuilder();
        sink
                .observe(s -> sleep(100)) //long-time operation
                .consume(sb::append);

        //do
        sink.onNext("a");
        sink.onNext("b");

        //assert
        sleep(500);//wait while broadcaster finished (if comment this line then the test will fail)
        assertEquals("ab", sb.toString());
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
