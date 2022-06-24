package hystrix.command.circuitbreaker;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import hystrix.helloworld.HelloWorldCommand;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CircuitBreakerTest {

    @Test
    void execute() {
//        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        String keyName = "abc";
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey(keyName);
        HystrixCircuitBreaker circuitBreaker = HystrixCircuitBreaker.Factory.getInstance(commandKey);
        HelloWorldCommand command = new HelloWorldCommand("Bob");
        String s = command.execute();
        assertThat(s, equalTo("Hello Bob!"));
    }

    @Test
    void queue() throws ExecutionException, InterruptedException {
        Future<String> future = new HelloWorldCommand("Bob").queue();
        String s = future.get();
        assertThat(s, equalTo("Hello Bob!"));
    }

    @Test
    void observe() {
        Observable<String> observable = new HelloWorldCommand("Bob").observe();
        String s = observable.toBlocking().first();
        assertThat(s, equalTo("Hello Bob!"));
    }
}
