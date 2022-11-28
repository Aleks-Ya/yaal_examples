package failsafe;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeException;
import dev.failsafe.RetryPolicy;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExampleTest {
    @Test
    void retry() {
        var resultExp = "abc";
        Callable<String> callable = new RepetitionsUnstableLogic<>(resultExp, 2);
        var retryPolicy = RetryPolicy.builder()
                .handle(ConnectException.class)
                .withDelay(Duration.ofMillis(500))
                .withMaxRetries(3)
                .build();

        var resultAct = Failsafe.with(retryPolicy).get(callable::call);
        assertThat(resultAct).isEqualTo(resultExp);
    }

    @Test
    void exceedMaxRetry() {
        var resultExp = "abc";
        var maxRetries = 3;
        var failedRepetitions = maxRetries + 1;
        Callable<String> callable = new RepetitionsUnstableLogic<>(resultExp, failedRepetitions);
        var retryPolicy = RetryPolicy.builder()
                .handle(ConnectException.class)
                .withDelay(Duration.ofMillis(500))
                .withMaxRetries(maxRetries)
                .build();

        assertThatThrownBy(() -> Failsafe.with(retryPolicy).get(callable::call))
                .isInstanceOf(FailsafeException.class)
                .hasMessage("java.net.ConnectException: abc")
                .cause()
                .isInstanceOf(ConnectException.class)
                .hasMessage("abc");
    }

}