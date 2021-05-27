package scheduling.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
class ThreadPoolTaskExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        var pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        return pool;
    }
}