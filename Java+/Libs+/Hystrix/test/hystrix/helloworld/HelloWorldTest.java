package hystrix.helloworld;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldTest {

    @Test
    void execute() {
        var s = new HelloWorldCommand("Bob").execute();
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
