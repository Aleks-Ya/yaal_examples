package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Process result of {@link CompletableFuture} execution.
 */
class ProcessResultOfCompletableFutureTest {

    @Test
    void thenApply() {
        var message = "Hello";
        var cf = CompletableFuture
                .supplyAsync(() -> message)
                .thenApply(String::toUpperCase);
        assertThat(cf.join()).isEqualTo(message.toUpperCase());
        assertThat(cf.isDone()).isEqualTo(true);
    }

    @Test
    void thenAccept() {
        var sb = new StringBuilder();
        var message = "Hello";
        var cf = CompletableFuture
                .supplyAsync(() -> message)
                .thenAccept(sb::append);
        cf.join();
        assertThat(sb.toString()).isEqualTo(message);
        assertThat(cf.isDone()).isEqualTo(true);
    }

    @Test
    void thenRun() throws ExecutionException, InterruptedException {
        var sb = new StringBuilder();
        var message = "Hello";
        var cf = CompletableFuture
                .supplyAsync(() -> message)
                .thenRun(() -> sb.append(message));
        cf.get();
        assertThat(sb.toString()).isEqualTo(message);
        assertThat(cf.isDone()).isEqualTo(true);
    }

}
