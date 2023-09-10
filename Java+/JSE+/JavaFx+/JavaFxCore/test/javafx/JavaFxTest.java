package javafx;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Combining {@link CompletableFuture}s to a chain.
 */
class JavaFxTest {

    @Test
    void thenCompose() {
        var cf = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenCompose(message -> CompletableFuture.supplyAsync(() -> message + " World"));
        assertThat(cf.join()).isEqualTo("Hello World");
        assertThat(cf.isDone()).isEqualTo(true);
    }

    @Test
    void thenAcceptBoth() {
        var sb = new StringBuilder();
        var cf = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> "World"),
                        (s1, s2) -> sb.append(s1).append(" ").append(s2));
        cf.join();
        assertThat(sb.toString()).isEqualTo("Hello World");
        assertThat(cf.isDone()).isEqualTo(true);
    }

}
