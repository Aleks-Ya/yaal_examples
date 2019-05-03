package async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
public class AsyncServiceTest {
    @Autowired
    private AsyncService asyncService;

    @Test
    public void withReturnType() throws ExecutionException, InterruptedException {
        Future<String> stringFuture = asyncService.asyncMethodWithReturnType();
        String value = stringFuture.get();
        assertThat(value, equalTo("hello world !!!!"));
    }

    @Test
    public void voidReturnType() {
        asyncService.asyncMethodWithVoidReturnType();
    }
}
