package retry;

import com.evanlennick.retry4j.CallExecutor;
import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class HandlingFailuresWithListenersTest {
    private static final Logger log = LoggerFactory.getLogger(HandlingFailuresWithListenersTest.class);

    @Test
    void retry() {
        var resultExp = "abc";
        Callable<String> callable = new UnstableLogic<>(resultExp, 2);

        var config = new RetryConfigBuilder()
                .exponentialBackoff5Tries5Sec()
                .build();

        CallExecutor<String> executor = new CallExecutorBuilder<>()
                .config(config)
                .onSuccessListener(s -> log.info("Success listener is invoked."))
                .onFailureListener(s -> log.info("Failure listener is invoked."))
                .afterFailedTryListener(s -> log.info("After Failed Try listener is invoked."))
                .beforeNextTryListener(s -> log.info("Before Failed Try listener is invoked."))
                .onCompletionListener(s -> log.info("On Completion listener is invoked."))
                .build();

        var status = executor.execute(callable);
        var resultAct = status.getResult();
        assertThat(resultAct).isEqualTo(resultExp);
    }
}