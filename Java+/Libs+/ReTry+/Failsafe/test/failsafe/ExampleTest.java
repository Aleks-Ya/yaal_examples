package failsafe;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class ExampleTest {

    @Test
    void retry() {
        var resultExp = "abc";
        Callable<String> callable = new UnstableLogic<>(resultExp, 2);
        var retryPolicy = RetryPolicy.builder()
                .handle(ConnectException.class)
                .withDelay(Duration.ofSeconds(1))
                .withMaxRetries(3)
                .build();

        var resultAct = Failsafe.with(retryPolicy).get(callable::call);
        assertThat(resultAct).isEqualTo(resultExp);
    }
}