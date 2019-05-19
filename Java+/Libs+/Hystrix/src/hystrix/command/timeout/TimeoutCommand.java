package hystrix.command.timeout;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class TimeoutCommand extends HystrixCommand<Void> {

    TimeoutCommand(String commandKey, String commandGroupKey) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandGroupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey)));
    }

    TimeoutCommand(String commandKey, String commandGroupKey, int executionIsolationThreadTimeoutInMilliseconds) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandGroupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(executionIsolationThreadTimeoutInMilliseconds)));
    }

    @Override
    protected Void run() throws InterruptedException {
        //noinspection InfiniteLoopStatement
        while (true) {
            Thread.sleep(100);
        }
    }
}
