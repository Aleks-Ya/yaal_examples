package java8.concurrent.future.completable;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Execute {@link CompletableFuture}.
 */
public class RunCompletableFuture {

    @Test
    public void runByExecutorService() {
        var cf = new CompletableFuture<String>();
        var message = "Hello";
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            cf.complete(message);
            return null;
        });
        assertThat(cf.join(), equalTo(message));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void runSupplierByCommonPool() {
        var message = "Hello";
        Supplier<String> supplier = () -> message;
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(supplier);
        assertThat(cf.join(), equalTo(message));
        assertThat(cf.isDone(), equalTo(true));
    }

    @Test
    public void runRunnableByCommonPool() {
        var sb = new StringBuilder();
        var message = "Hi";
        Runnable runnable = () -> sb.append(message);
        CompletableFuture<Void> cf = CompletableFuture.runAsync(runnable);
        cf.join();
        assertThat(sb.toString(), equalTo(message));
        assertThat(cf.isDone(), equalTo(true));
    }

}
