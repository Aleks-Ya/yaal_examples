package retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class DecorateTest {
    @Test
    void decorateCallable() throws Exception {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var resultExp = "abc";
        Callable<String> callable = new UnstableCallable<>(resultExp, 2);
        var retryable = Retry.decorateCallable(retry, callable);
        var resultAct = retryable.call();
        assertThat(resultAct).isEqualTo(resultExp);
    }

    @Test
    void decorateRunnable() {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var runnable = new UnstableRunnable(2);
        var retryable = Retry.decorateRunnable(retry, runnable);
        retryable.run();
        assertThat(runnable.getCounter().get()).isEqualTo(3);
    }

    @Test
    void decorateSupplier() {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var resultExp = "abc";
        var supplier = new UnstableSupplier<>(resultExp, 2);
        var retryable = Retry.decorateSupplier(retry, supplier);
        var resultAct = retryable.get();
        assertThat(resultAct).isEqualTo(resultExp);
    }

    @Test
    void decorateFunction() {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var function = new UnstableFunction(2);
        var retryable = Retry.decorateFunction(retry, function);
        var resultAct = retryable.apply(123);
        assertThat(resultAct).isEqualTo("123");
    }
}