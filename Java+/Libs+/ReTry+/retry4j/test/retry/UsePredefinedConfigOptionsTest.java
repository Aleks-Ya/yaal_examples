package retry;

import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.Status;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class UsePredefinedConfigOptionsTest {

    @Test
    void retry() {
        var resultExp = "abc";
        Callable<String> callable = new UnstableLogic<>(resultExp, 2);

        var config = new RetryConfigBuilder()
                .exponentialBackoff5Tries5Sec()
                .build();

        Status<String> status = new CallExecutorBuilder().config(config).build().execute(callable);
        var resultAct = status.getResult();
        assertThat(resultAct).isEqualTo(resultExp);
    }
}