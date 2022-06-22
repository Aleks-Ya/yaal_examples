package util.concurrent.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Create instance of {@link Future}.
 */
class InstantiateFutureTest {

    @Test
    void submitToExecutorService() throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> 2 * 3);
        var result = future.get();
        assertThat(result).isEqualTo(6);
    }

    @Test
    void futureTask() throws ExecutionException, InterruptedException {
        var future = new FutureTask<>(() -> 2 * 3);
        future.run();
        var result = future.get();
        assertThat(result).isEqualTo(6);
    }

}
