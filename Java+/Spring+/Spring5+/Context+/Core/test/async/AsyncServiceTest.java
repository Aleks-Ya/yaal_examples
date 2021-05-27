package async;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
class AsyncServiceTest {
    @Autowired
    private AsyncService asyncService;

    @Test
    void withReturnType() throws ExecutionException, InterruptedException {
        var stringFuture = asyncService.asyncMethodWithReturnType();
        var value = stringFuture.get();
        assertThat(value, equalTo("hello world !!!!"));
    }

    @Test
    void voidReturnType() {
        asyncService.asyncMethodWithVoidReturnType();
    }
}
