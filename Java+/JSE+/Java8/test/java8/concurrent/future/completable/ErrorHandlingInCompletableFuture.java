package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Handle error in {@link CompletableFuture}s.
 */
public class ErrorHandlingInCompletableFuture {
    private Boolean errorHappened;

    @Test
    public void notHandleExceptions() {
        var expException = new IllegalArgumentException();
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw expException;
                });
        var actException = assertThrows(ExecutionException.class, cf::get);
        assertThat(cf.isDone(), equalTo(true));
        assertThat(actException.getCause(), equalTo(expException));
    }

    @Test
    public void handle() {
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw new IllegalArgumentException();
                })
                .handle((s, t) -> s != null ? s : "Hello Exception");
        assertThat(cf.join(), equalTo("Hello Exception"));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void whenComplete() {
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw new IllegalArgumentException();
                })
                .whenComplete((msg, t) -> {
                    errorHappened = t != null;
                });
        assertThat(errorHappened, equalTo(true));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void exceptionally() {
        var cf = CompletableFuture
                .supplyAsync(() -> {
                    throw new IllegalArgumentException();
                }).exceptionally(t -> errorHappened = true);
        assertThat(errorHappened, equalTo(true));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void completeExceptionally() {
        var cf = CompletableFuture.supplyAsync(() -> "The result");
        var expException = new RuntimeException("Computation failed");
        cf.completeExceptionally(expException);
        assertThat(cf.isDone(), equalTo(true));
        var actException = assertThrows(ExecutionException.class, cf::get);
        assertThat(actException.getCause(), equalTo(expException));
    }

}
