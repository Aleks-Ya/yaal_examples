package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Create instance of {@link CompletableFuture}.
 */
class InstantiateCompletableFutureTest {
    @Test
    void completedFuture() {
        var cf = CompletableFuture.completedFuture("message");
        assertThat(cf.isDone()).isEqualTo(true);
        assertThat(cf.getNow(null)).isEqualTo("message");
    }

    @Test
    void failedFuture() {
        Exception eExp = new IllegalArgumentException("my argument");
        CompletableFuture<String> cf = CompletableFuture.failedFuture(eExp);
        assertThat(cf.isDone()).isEqualTo(true);
        assertThat(cf.isCompletedExceptionally()).isEqualTo(true);
        var eAct = assertThatThrownBy(() -> cf.getNow(null))
                .isInstanceOf(CompletionException.class)
                .hasCause(eExp);
    }

    @Test
    void notCompletedFuture() {
        var cf = new CompletableFuture<String>();
        assertThat(cf.isDone()).isEqualTo(false);
        var message = "Hello";
        cf.complete(message);
        assertThat(cf.isDone()).isEqualTo(true);
        assertThat(cf.join()).isEqualTo(message);
    }
}
