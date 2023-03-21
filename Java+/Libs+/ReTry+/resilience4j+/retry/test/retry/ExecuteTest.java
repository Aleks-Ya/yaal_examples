package retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class ExecuteTest {
    @Test
    void executeCallable() throws Exception {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var resultExp = "abc";
        Callable<String> callable = new UnstableCallable<>(resultExp, 2);
        var resultAct = retry.executeCallable(callable);
        assertThat(resultAct).isEqualTo(resultExp);
    }

    @Test
    void executeRunnable() {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var runnable = new UnstableRunnable(2);
        retry.executeRunnable(runnable);
        assertThat(runnable.getCounter().get()).isEqualTo(3);
    }

    @Test
    void executeSupplier() {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var resultExp = "abc";
        var supplier = new UnstableSupplier<>(resultExp, 2);
        var resultAct = retry.executeSupplier(supplier);
        assertThat(resultAct).isEqualTo(resultExp);
    }

    @Test
    void executeCheckedSupplier() throws Throwable {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var resultExp = "abc";
        var supplier = new UnstableCheckedSupplier<>(resultExp, 2);
        var resultAct = retry.executeCheckedSupplier(supplier);
        assertThat(resultAct).isEqualTo(resultExp);
    }
}