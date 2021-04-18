package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Combining {@link CompletableFuture}s to a chain.
 */
public class CombineCompletableFuture {

    @Test
    public void thenCompose() {
        var cf = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenCompose(message -> CompletableFuture.supplyAsync(() -> message + " World"));
        assertThat(cf.join(), equalTo("Hello World"));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void thenAcceptBoth() {
        var sb = new StringBuilder();
        var cf = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> "World"),
                        (s1, s2) -> sb.append(s1).append(" ").append(s2));
        cf.join();
        assertThat(sb.toString(), equalTo("Hello World"));
        assertThat(cf.isDone(), equalTo(true));
    }

}
