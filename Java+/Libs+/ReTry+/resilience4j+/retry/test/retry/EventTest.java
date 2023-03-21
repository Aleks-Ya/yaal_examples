package retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.event.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    void allEvents() throws Exception {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);

        var onRetryEvents = new ArrayList<RetryOnRetryEvent>();
        var onSuccessEvents = new ArrayList<RetryOnSuccessEvent>();
        var onErrorEvents = new ArrayList<RetryOnErrorEvent>();
        var onIgnoredErrorEvents = new ArrayList<RetryOnIgnoredErrorEvent>();
        var onEventEvents = new ArrayList<RetryEvent>();

        var eventPublisher = retry.getEventPublisher();
        eventPublisher.onRetry(event -> {
            System.out.println("RetryOnRetryEvent: " + event);
            onRetryEvents.add(event);
        });
        eventPublisher.onSuccess(event -> {
            System.out.println("RetryOnSuccessEvent: " + event);
            onSuccessEvents.add(event);
        });
        eventPublisher.onError(event -> {
            System.out.println("RetryOnErrorEvent: " + event);
            onErrorEvents.add(event);
        });
        eventPublisher.onIgnoredError(event -> {
            System.out.println("RetryOnIgnoredErrorEvent: " + event);
            onIgnoredErrorEvents.add(event);
        });
        eventPublisher.onEvent(event -> {
            System.out.println("RetryEvent: " + event);
            onEventEvents.add(event);
        });

        retry.executeCallable(new UnstableCallable<>("abc", 2));

        assertThat(onRetryEvents).hasSize(2);
        assertThat(onSuccessEvents).hasSize(1);
        assertThat(onErrorEvents).hasSize(0);
        assertThat(onIgnoredErrorEvents).hasSize(0);
        assertThat(onEventEvents).hasSize(3);
    }

    @Test
    void logOnRetry() throws Exception {
        var retryConfig = RetryConfig.custom().maxAttempts(5).build();
        var retry = Retry.of("pingpong", retryConfig);
        var events = new ArrayList<RetryOnRetryEvent>();
        retry.getEventPublisher().onRetry(event -> {
            System.out.println("Retry event: " + event);
            events.add(event);
        });
        retry.executeCallable(new UnstableCallable<>("abc", 2));
        assertThat(events).hasSize(2);
    }
}