package scheduling.threadpool;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Использование TaskScheduler для запуска задач по расписанию.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ThreadPoolTaskExecutorConfig.class)
public class ThreadPoolTaskExecutorTest {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private volatile String runnableThreadName;

    @Test
    public void test() throws InterruptedException, ExecutionException {
        Runnable runnable = () -> {
            try {
                runnableThreadName = Thread.currentThread().getName();
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Callable<String> callable = () -> {
            Thread.sleep(300);
            return Thread.currentThread().getName();
        };

        taskExecutor.execute(runnable);
        Future<String> callableFuture = taskExecutor.submit(callable);

        //noinspection StatementWithEmptyBody
        while (runnableThreadName == null) ;

        assertThat(runnableThreadName, equalTo("taskExecutor-1"));
        assertThat(callableFuture.get(), equalTo("taskExecutor-2"));

    }
}