package java8.concurrent.future.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Handle error in {@link CompletableFuture}s.
 */
public class ErrorHandlingInCompletableFuture {

    @Test
    public void handle() {
        String message = null;
        var cf = CompletableFuture.supplyAsync(() -> {
            if (message == null) {
                throw new IllegalArgumentException();
            }
            return message.toUpperCase();
        }).handle((s, t) -> s != null ? s : "Hello Exception");
        assertThat(cf.join(), equalTo("Hello Exception"));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void completeExceptionally() {
        var cf = new CompletableFuture<String>();
        var expException = new RuntimeException("Computation failed");
        cf.completeExceptionally(expException);
        assertThat(cf.isDone(), equalTo(true));
        var actException = assertThrows(ExecutionException.class, cf::get);
        assertThat(actException.getCause(), equalTo(expException));
    }

}
