package java8.concurrent.future.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Process result of {@link CompletableFuture} execution.
 */
public class ProcessResultOfCompletableFuture {

    @Test
    public void thenApply() {
        var message = "Hello";
        var cf = CompletableFuture
                .supplyAsync(() -> message)
                .thenApply(String::toUpperCase);
        assertThat(cf.join(), equalTo(message.toUpperCase()));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void thenAccept() {
        var sb = new StringBuilder();
        var message = "Hello";
        var cf = CompletableFuture
                .supplyAsync(() -> message)
                .thenAccept(sb::append);
        cf.join();
        assertThat(sb.toString(), equalTo(message));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void thenRun() throws ExecutionException, InterruptedException {
        var sb = new StringBuilder();
        var message = "Hello";
        var cf = CompletableFuture
                .supplyAsync(() -> message)
                .thenRun(() -> sb.append(message));
        cf.get();
        assertThat(sb.toString(), equalTo(message));
        assertThat(cf.isDone(), equalTo(true));
    }

}
