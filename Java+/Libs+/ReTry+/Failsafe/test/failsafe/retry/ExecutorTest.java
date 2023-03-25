package failsafe.retry;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import failsafe.ListUnstableCallable;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class ExecutorTest {
    @Test
    void oneExecutorForMultipleCallable() {
        var expResult1 = "abc";
        var expResult2 = "xyz";
        Callable<String> callable1 = new ListUnstableCallable<>(
                List.of(new ConnectException("ex1"), new ArithmeticException("ex2")), expResult1);
        Callable<String> callable2 = new ListUnstableCallable<>(
                List.of(new ArithmeticException("ex3"), new ConnectException("ex4")), expResult2);
        var retryPolicy = RetryPolicy.builder()
                .handle(ConnectException.class, ArithmeticException.class)
                .withDelay(Duration.ofMillis(500))
                .withMaxRetries(3)
                .build();
        var executor = Failsafe.with(retryPolicy);
        assertThat(executor.get(callable1::call)).isEqualTo(expResult1);
        assertThat(executor.get(callable2::call)).isEqualTo(expResult2);
    }
}