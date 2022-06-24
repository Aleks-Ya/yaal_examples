package hystrix.helloworld;

import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HelloWorldTest {

    @Test
    void execute() {
        String s = new HelloWorldCommand("Bob").execute();
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
