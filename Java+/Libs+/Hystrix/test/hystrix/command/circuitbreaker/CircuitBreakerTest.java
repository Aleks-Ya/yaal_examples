package hystrix.command.circuitbreaker;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import hystrix.helloworld.HelloWorldCommand;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class CircuitBreakerTest {

    @Test
    void execute() {
//        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        var keyName = "abc";
        var commandKey = HystrixCommandKey.Factory.asKey(keyName);
        var circuitBreaker = HystrixCircuitBreaker.Factory.getInstance(commandKey);
        var command = new HelloWorldCommand("Bob");
        var s = command.execute();
        assertThat(s).isEqualTo("Hello Bob!");
    }

    @Test
    void queue() throws ExecutionException, InterruptedException {
        var future = new HelloWorldCommand("Bob").queue();
        var s = future.get();
        assertThat(s).isEqualTo("Hello Bob!");
    }

    @Test
    void observe() {
        var observable = new HelloWorldCommand("Bob").observe();
        var s = observable.toBlocking().first();
        assertThat(s).isEqualTo("Hello Bob!");
    }
}
