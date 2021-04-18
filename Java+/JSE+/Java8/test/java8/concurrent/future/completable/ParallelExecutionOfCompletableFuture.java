package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Execute several {@link CompletableFuture}s in parallel.
 */
public class ParallelExecutionOfCompletableFuture {

    @Test
    public void thenCompose() {
        var cf1 = CompletableFuture.supplyAsync(() -> "Hello");
        var cf2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        var cf3 = CompletableFuture.supplyAsync(() -> "World");
        var combined = CompletableFuture.allOf(cf1, cf2, cf3);
        combined.join();
        assertThat(cf1.isDone(), equalTo(true));
        assertThat(cf2.isDone(), equalTo(true));
        assertThat(cf3.isDone(), equalTo(true));
        String str = Stream.of(cf1, cf2, cf3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        assertThat(str, equalTo("Hello Beautiful World"));
    }

}
