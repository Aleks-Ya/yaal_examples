import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class WaitForTimeTest {

    @Test
    void pollDelay() {
        var start = System.currentTimeMillis();
        var time = 1000;
        await().pollDelay(time, MILLISECONDS).until(() -> true);
        var end = System.currentTimeMillis();
        var diff = end - start;
        assertThat(diff).isGreaterThanOrEqualTo(time);
    }
}
