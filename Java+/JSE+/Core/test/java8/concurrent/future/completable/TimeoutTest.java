package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeoutTest {
    @Test
    void timeout() {
        assertThatThrownBy(() ->
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).orTimeout(300, TimeUnit.MILLISECONDS).join())
                .isInstanceOf(CompletionException.class)
                .cause().isInstanceOf(TimeoutException.class);
    }

}
