package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Execute several {@link CompletableFuture}s in parallel.
 */
class ParallelExecutionOfCompletableFutureTest {

    @Test
    void thenCompose() {
        var cf1 = CompletableFuture.supplyAsync(() -> "Hello");
        var cf2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        var cf3 = CompletableFuture.supplyAsync(() -> "World");
        var combined = CompletableFuture.allOf(cf1, cf2, cf3);
        combined.join();
        assertThat(cf1.isDone()).isEqualTo(true);
        assertThat(cf2.isDone()).isEqualTo(true);
        assertThat(cf3.isDone()).isEqualTo(true);
        String str = Stream.of(cf1, cf2, cf3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        assertThat(str).isEqualTo("Hello Beautiful World");
    }

}
