package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Handle error in {@link CompletableFuture}s.
 */
class ErrorHandlingInCompletableFutureTest {
    private Boolean errorHappened;

    @Test
    void notHandleExceptions() {
        var expException = new IllegalArgumentException();
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw expException;
                });
        assertThatThrownBy(cf::get)
                .isInstanceOf(ExecutionException.class)
                .hasCause(expException);
        assertThat(cf.isDone()).isEqualTo(true);
    }

    @Test
    void handle() {
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw new IllegalArgumentException();
                })
                .handle((s, t) -> s != null ? s : "Hello Exception");
        assertThat(cf.join()).isEqualTo("Hello Exception");
        assertThat(cf.isDone()).isEqualTo(true);
    }

    @Test
    void whenComplete() {
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw new IllegalArgumentException();
                })
                .whenComplete((msg, t) -> errorHappened = t != null);
        assertThat(errorHappened).isEqualTo(true);
        assertThat(cf.isDone()).isEqualTo(true);
    }

    @Test
    void exceptionally() {
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw new IllegalArgumentException();
                }).exceptionally(t -> errorHappened = true);
        assertThat(errorHappened).isEqualTo(true);
        assertThat(cf.isDone()).isEqualTo(true);
    }

    @Test
    void completeExceptionally() {
        var cf = CompletableFuture.supplyAsync(() -> "The result");
        var expException = new RuntimeException("Computation failed");
        cf.completeExceptionally(expException);
        assertThat(cf.isDone()).isEqualTo(true);
        assertThatThrownBy(cf::get)
                .isInstanceOf(ExecutionException.class)
                .hasCause(expException);
    }

}
