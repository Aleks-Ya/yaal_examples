package timelimiter;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.event.TimeLimiterEvent;
import io.github.resilience4j.timelimiter.event.TimeLimiterOnErrorEvent;
import io.github.resilience4j.timelimiter.event.TimeLimiterOnSuccessEvent;
import io.github.resilience4j.timelimiter.event.TimeLimiterOnTimeoutEvent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventTest {
    @Test
    void allEvents() {
        var timeLimiter = TimeLimiter.of(ofMillis(200));
        var cf = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var onTimeoutEvents = new ArrayList<TimeLimiterOnTimeoutEvent>();
        var onErrorEvents = new ArrayList<TimeLimiterOnErrorEvent>();
        var onSuccessEvents = new ArrayList<TimeLimiterOnSuccessEvent>();
        var onEventEvents = new ArrayList<TimeLimiterEvent>();
        var publisher = timeLimiter.getEventPublisher();
        publisher.onTimeout(event -> {
            System.out.println("TimeLimiterOnTimeoutEvent: " + event);
            onTimeoutEvents.add(event);
        });
        publisher.onError(event -> {
            System.out.println("TimeLimiterOnErrorEvent: " + event);
            onErrorEvents.add(event);
        });
        publisher.onSuccess(event -> {
            System.out.println("TimeLimiterOnSuccessEvent: " + event);
            onSuccessEvents.add(event);
        });
        publisher.onEvent(event -> {
            System.out.println("TimeLimiterEvent: " + event);
            onEventEvents.add(event);
        });
        assertThatThrownBy(() -> timeLimiter.executeFutureSupplier(() -> cf))
                .isInstanceOf(TimeoutException.class);
        assertThat(onTimeoutEvents).hasSize(1);
        assertThat(onSuccessEvents).hasSize(0);
        assertThat(onErrorEvents).hasSize(0);
        assertThat(onEventEvents).hasSize(1);
    }
}