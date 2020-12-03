package util.concurrent.future;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Create instance of {@link Future}.
 */
public class InstantiateFuture {

    @Test
    public void submitToExecutorService() throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> 2 * 3);
        var result = future.get();
        assertThat(result, equalTo(6));
    }

    @Test
    public void futureTask() throws ExecutionException, InterruptedException {
        var future = new FutureTask<>(() -> 2 * 3);
        future.run();
        var result = future.get();
        assertThat(result, equalTo(6));
    }

}
