package async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
class AsyncService {
    @Async("threadPoolTaskExecutor")
    void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());
    }

    @Async("threadPoolTaskExecutor")
    Future<String> asyncMethodWithReturnType() throws InterruptedException {
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
        Thread.sleep(5000);
        return new AsyncResult<>("hello world !!!!");
    }
}
