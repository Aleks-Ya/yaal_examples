package failsafe;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class DifferentExceptionsTest {
    @Test
    void samePolicy() {
        var resultExp = "abc";
        var exceptions = List.of(new ConnectException("ex1"), new ArithmeticException("ex2"));
        Callable<String> callable = new ListUnstableLogic<>(exceptions, resultExp);
        var retryPolicy = RetryPolicy.builder()
                .handle(ConnectException.class, ArithmeticException.class)
                .withDelay(Duration.ofMillis(500))
                .withMaxRetries(10)
                .build();
        var resultAct = Failsafe.with(retryPolicy).get(callable::call);
        assertThat(resultAct).isEqualTo(resultExp);
    }

    @Test
    void differentPolicy() {
        var resultExp = "abc";
        var exceptions = List.of(new ConnectException("ex1"), new ArithmeticException("ex2"));
        Callable<String> callable = new ListUnstableLogic<>(exceptions, resultExp);
        var retryPolicy = RetryPolicy.builder()
                .handle(ConnectException.class)
                .withDelay(Duration.ofMillis(500))
                .withMaxRetries(10)
                .handle(ArithmeticException.class)
                .withDelay(Duration.ofMillis(300))
                .withMaxRetries(20)
                .build();

        var resultAct = Failsafe.with(retryPolicy).get(callable::call);
        assertThat(resultAct).isEqualTo(resultExp);
    }

}