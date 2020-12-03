package java8.concurrent.future.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Create instance of {@link CompletableFuture}.
 */
public class InstantiateCompletableFuture {
    @Test
    public void completedFuture() {
        var cf = CompletableFuture.completedFuture("message");
        assertThat(cf.isDone(), equalTo(true));
        assertThat(cf.getNow(null), equalTo("message"));
    }

    @Test
    public void failedFuture() {
        Exception eExp = new IllegalArgumentException("my argument");
        CompletableFuture<String> cf = CompletableFuture.failedFuture(eExp);
        assertThat(cf.isDone(), equalTo(true));
        assertThat(cf.isCompletedExceptionally(), equalTo(true));
        var eAct = assertThrows(CompletionException.class, () -> cf.getNow(null));
        assertThat(eAct.getCause(), equalTo(eExp));
    }

    @Test
    public void notCompletedFuture() {
        var cf = new CompletableFuture<String>();
        assertThat(cf.isDone(), equalTo(false));
        var message = "Hello";
        cf.complete(message);
        assertThat(cf.isDone(), equalTo(true));
        assertThat(cf.join(), equalTo(message));
    }
}
