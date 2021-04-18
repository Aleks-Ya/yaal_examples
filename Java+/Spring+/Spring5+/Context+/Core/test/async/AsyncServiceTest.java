package async;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
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
