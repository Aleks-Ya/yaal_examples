package failsafe.retry;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeException;
import dev.failsafe.RetryPolicy;
import dev.failsafe.RetryPolicyBuilder;
import failsafe.ListUnstableCallable;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Collections.synchronizedList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventListenerTest {
    private final List<String> onRetryEvents = synchronizedList(new ArrayList<>());
    private final List<String> onFailedAttemptEvents = synchronizedList(new ArrayList<>());
    private final List<String> onRetryScheduledEvents = synchronizedList(new ArrayList<>());
    private final List<String> onAbortEvents = synchronizedList(new ArrayList<>());
    private final List<String> onRetriesExceededEvents = synchronizedList(new ArrayList<>());
    private final List<String> onFailureEvents = synchronizedList(new ArrayList<>());
    private final List<String> onSuccessEvents = synchronizedList(new ArrayList<>());

    private <R> RetryPolicyBuilder<R> addListeners(RetryPolicyBuilder<R> in) {
        return in.onRetry(event -> onRetryEvents.add(event.toString()))
                .onFailedAttempt(event -> onFailedAttemptEvents.add(event.toString()))
                .onRetryScheduled(event -> onRetryScheduledEvents.add(event.toString()))
                .onAbort(event -> onAbortEvents.add(event.toString()))
                .onRetriesExceeded(event -> onRetriesExceededEvents.add(event.toString()))
                .onFailure(event -> onFailureEvents.add(event.toString()))
                .onSuccess(event -> onSuccessEvents.add(event.toString()));
    }

    @Test
    void onSuccess() {
        var resultExp = "abc";
        var exceptions = List.of(new ConnectException("ex1"), new ArithmeticException("ex2"));
        Callable<String> callable = new ListUnstableCallable<>(exceptions, resultExp);
        var retryBuilder = RetryPolicy.builder()
                .handle(ConnectException.class, ArithmeticException.class)
                .withDelay(Duration.ofMillis(500))
                .withMaxRetries(10);
        var retryPolicy = addListeners(retryBuilder).build();
        var resultAct = Failsafe.with(retryPolicy).get(callable::call);
        assertThat(resultAct).isEqualTo(resultExp);
        assertThat(onRetryEvents).containsExactly(
                "ExecutionAttemptedEvent[result=null, exception=java.net.ConnectException: ex1]",
                "ExecutionAttemptedEvent[result=null, exception=java.lang.ArithmeticException: ex2]");
        assertThat(onFailedAttemptEvents).containsExactly(
                "ExecutionAttemptedEvent[result=null, exception=java.net.ConnectException: ex1]",
                "ExecutionAttemptedEvent[result=null, exception=java.lang.ArithmeticException: ex2]");
        assertThat(onRetryScheduledEvents).containsExactly(
                "ExecutionScheduledEvent[result=null, exception=java.net.ConnectException: ex1, delay=PT0.5S]",
                "ExecutionScheduledEvent[result=null, exception=java.lang.ArithmeticException: ex2, delay=PT0.5S]");
        assertThat(onAbortEvents).isEmpty();
        assertThat(onRetriesExceededEvents).isEmpty();
        assertThat(onFailureEvents).isEmpty();
        assertThat(onSuccessEvents).containsExactly("ExecutionCompletedEvent[result=abc, exception=null]");
    }

    @Test
    void onFailure() {
        var resultExp = "abc";
        var exceptions = List.of(new ConnectException("ex1"), new ConnectException("ex2"), new ConnectException("ex3"));
        Callable<String> callable = new ListUnstableCallable<>(exceptions, resultExp);
        var retryBuilder = RetryPolicy.builder()
                .handle(ConnectException.class)
                .withDelay(Duration.ofMillis(500))
                .withMaxRetries(2);
        var retryPolicy = addListeners(retryBuilder).build();
        assertThatThrownBy(() -> Failsafe.with(retryPolicy).get(callable::call)).isInstanceOf(FailsafeException.class);
        assertThat(onRetryEvents).containsExactly(
                "ExecutionAttemptedEvent[result=null, exception=java.net.ConnectException: ex1]",
                "ExecutionAttemptedEvent[result=null, exception=java.net.ConnectException: ex2]");
        assertThat(onFailedAttemptEvents).containsExactly(
                "ExecutionAttemptedEvent[result=null, exception=java.net.ConnectException: ex1]",
                "ExecutionAttemptedEvent[result=null, exception=java.net.ConnectException: ex2]",
                "ExecutionAttemptedEvent[result=null, exception=java.net.ConnectException: ex3]");
        assertThat(onRetryScheduledEvents).containsExactly(
                "ExecutionScheduledEvent[result=null, exception=java.net.ConnectException: ex1, delay=PT0.5S]",
                "ExecutionScheduledEvent[result=null, exception=java.net.ConnectException: ex2, delay=PT0.5S]");
        assertThat(onAbortEvents).isEmpty();
        assertThat(onRetriesExceededEvents).containsExactly(
                "ExecutionCompletedEvent[result=null, exception=java.net.ConnectException: ex3]");
        assertThat(onFailureEvents).containsExactly(
                "ExecutionCompletedEvent[result=null, exception=java.net.ConnectException: ex3]");
        assertThat(onSuccessEvents).isEmpty();
    }

}