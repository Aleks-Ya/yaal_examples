package scheduling.threadpool;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование TaskScheduler для запуска задач по расписанию.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ThreadPoolTaskExecutorConfig.class)
class ThreadPoolTaskExecutorTest {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private volatile String runnableThreadName;

    @Test
    void test() throws InterruptedException, ExecutionException {
        var runnable = (Runnable) () -> {
            try {
                runnableThreadName = Thread.currentThread().getName();
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        var callable = (Callable<String>) () -> {
            Thread.sleep(300);
            return Thread.currentThread().getName();
        };

        taskExecutor.execute(runnable);
        var callableFuture = taskExecutor.submit(callable);

        //noinspection StatementWithEmptyBody
        while (runnableThreadName == null) ;

        assertThat(runnableThreadName).isEqualTo("taskExecutor-1");
        assertThat(callableFuture.get()).isEqualTo("taskExecutor-2");

    }
}