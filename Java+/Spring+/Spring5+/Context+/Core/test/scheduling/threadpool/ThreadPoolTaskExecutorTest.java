package scheduling.threadpool;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Использование TaskScheduler для запуска задач по расписанию.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ThreadPoolTaskExecutorTest.Config.class)
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

        await().until(() -> runnableThreadName != null);

        assertThat(runnableThreadName).isEqualTo("taskExecutor-1");
        assertThat(callableFuture.get()).isEqualTo("taskExecutor-2");
    }

    @Configuration
    static class Config {
        @Bean
        public ThreadPoolTaskExecutor taskExecutor() {
            var pool = new ThreadPoolTaskExecutor();
            pool.setCorePoolSize(5);
            pool.setMaxPoolSize(10);
            return pool;
        }
    }
}