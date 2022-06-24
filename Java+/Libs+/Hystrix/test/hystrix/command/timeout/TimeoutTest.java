package hystrix.command.timeout;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class TimeoutTest {
    private final String TIMEOUT_PROPERTY = "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds";
    String commandGroupKey = "defaultTimeout";

    @Test
    void defaultTimeout() {
        int executionIsolationThreadTimeoutInMilliseconds = 200;
        System.setProperty(TIMEOUT_PROPERTY, Integer.toString(executionIsolationThreadTimeoutInMilliseconds));
        String commandKey = "defaultTimeout";
        final TimeoutCommand command = new TimeoutCommand(commandKey, commandGroupKey);
        try {
            command.execute();
            fail();
        } catch (HystrixRuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(commandKey + " timed-out and no fallback available.");
            assertThat(command.isResponseTimedOut()).isTrue();
            assertThat(command.getExecutionTimeInMilliseconds()).isGreaterThanOrEqualTo(executionIsolationThreadTimeoutInMilliseconds);
        }
    }

    @Test
    void commandSpecificTimeoutViaConstructor() {
        int executionIsolationThreadTimeoutInMilliseconds = 1000;
        String commandKey = "commandSpecificTimeoutViaConstructor";
        final TimeoutCommand command = new TimeoutCommand(commandKey, commandGroupKey,
                executionIsolationThreadTimeoutInMilliseconds);
        try {
            command.execute();
            fail();
        } catch (HystrixRuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(commandKey + " timed-out and no fallback available.");
            assertThat(command.isResponseTimedOut()).isTrue();
            assertThat(command.getExecutionTimeInMilliseconds()).isGreaterThanOrEqualTo(executionIsolationThreadTimeoutInMilliseconds);
        }
    }

    @Test
    void commandSpecificTimeoutViaProperty() {
        int executionIsolationThreadTimeoutInMilliseconds = 2000;
        String commandKey = "commandSpecificTimeoutViaProperty";
        final String prop = TIMEOUT_PROPERTY.replace("default", commandKey);
//        final String prop = "hystrix.command.commandSpecificTimeoutViaProperty.execution.isolation.thread.timeoutInMilliseconds";
        System.setProperty(prop, Integer.toString(executionIsolationThreadTimeoutInMilliseconds));
        final TimeoutCommand command = new TimeoutCommand(commandKey, commandGroupKey);
        try {
            command.execute();
            fail();
        } catch (HystrixRuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(commandKey + " timed-out and no fallback available.");
            assertThat(command.isResponseTimedOut()).isTrue();
            assertThat(command.getExecutionTimeInMilliseconds()).isGreaterThanOrEqualTo(executionIsolationThreadTimeoutInMilliseconds);
        }
    }

}
