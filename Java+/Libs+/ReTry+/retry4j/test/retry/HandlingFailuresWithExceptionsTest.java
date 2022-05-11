package retry;

import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.Status;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

class HandlingFailuresWithExceptionsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlingFailuresWithExceptionsTest.class);

    @Test
    void retry() {
        var resultExp = "abc";
        Callable<String> callable = new UnstableLogic<>(resultExp, 2);

        var config = new RetryConfigBuilder()
                .retryOnSpecificExceptions(ConnectException.class)
                .withMaxNumberOfTries(10)
                .withDelayBetweenTries(2, ChronoUnit.SECONDS)
                .withExponentialBackoff()
                .build();

        try {
            Status<String> status = new CallExecutorBuilder()
                    .config(config)
                    .build()
                    .execute(callable);
            var resultAct = status.getResult();
            log.info("Success result: {}", resultAct);
            assertThat(resultAct).isEqualTo(resultExp);
        } catch (RetriesExhaustedException ree) {
            log.error("Retries exhausted", ree);
        } catch (UnexpectedException ue) {
            log.error("Unexpected exception", ue);
        }
    }
}