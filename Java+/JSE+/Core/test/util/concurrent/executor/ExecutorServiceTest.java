package util.concurrent.executor;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.assertj.core.api.Assertions.assertThat;

class ExecutorServiceTest {
    @Test
    void singleThreadExecutor() throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        assertThat(executor.submit(() -> 2 * 3).get()).isEqualTo(6);
        executor.shutdown();
    }
}
