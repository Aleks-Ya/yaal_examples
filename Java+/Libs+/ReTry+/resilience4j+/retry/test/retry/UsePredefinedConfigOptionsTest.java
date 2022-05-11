package retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class UsePredefinedConfigOptionsTest {

    @Test
    void retry() throws Exception {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var resultExp = "abc";
        Callable<String> callableOriginal = new UnstableLogic<>(resultExp, 2);
        var callableRetryable = Retry.decorateCallable(retry, callableOriginal);
        var resultAct = callableRetryable.call();
        assertThat(resultAct).isEqualTo(resultExp);
    }
}