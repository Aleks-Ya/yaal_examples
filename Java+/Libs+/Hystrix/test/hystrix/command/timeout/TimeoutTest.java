package hystrix.command.timeout;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TimeoutTest {
    private final String TIMEOUT_PROPERTY = "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds";
    String commandGroupKey = "defaultTimeout";

    @Test
    public void defaultTimeout() {
        int executionIsolationThreadTimeoutInMilliseconds = 200;
        System.setProperty(TIMEOUT_PROPERTY, Integer.toString(executionIsolationThreadTimeoutInMilliseconds));
        String commandKey = "defaultTimeout";
        final TimeoutCommand command = new TimeoutCommand(commandKey, commandGroupKey);
        try {
            command.execute();
            fail();
        } catch (HystrixRuntimeException e) {
            assertThat(e.getMessage(), equalTo(commandKey + " timed-out and no fallback available."));
            assertTrue(command.isResponseTimedOut());
            assertThat(command.getExecutionTimeInMilliseconds(), greaterThanOrEqualTo(executionIsolationThreadTimeoutInMilliseconds));
        }
    }

    @Test
    public void commandSpecificTimeoutViaConstructor() {
        int executionIsolationThreadTimeoutInMilliseconds = 1000;
        String commandKey = "commandSpecificTimeoutViaConstructor";
        final TimeoutCommand command = new TimeoutCommand(commandKey, commandGroupKey,
                executionIsolationThreadTimeoutInMilliseconds);
        try {
            command.execute();
            fail();
        } catch (HystrixRuntimeException e) {
            assertThat(e.getMessage(), equalTo(commandKey + " timed-out and no fallback available."));
            assertTrue(command.isResponseTimedOut());
            assertThat(command.getExecutionTimeInMilliseconds(), greaterThanOrEqualTo(executionIsolationThreadTimeoutInMilliseconds));
        }
    }

    @Test
    public void commandSpecificTimeoutViaProperty() {
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
            assertThat(e.getMessage(), equalTo(commandKey + " timed-out and no fallback available."));
            assertTrue(command.isResponseTimedOut());
            assertThat(command.getExecutionTimeInMilliseconds(), greaterThanOrEqualTo(executionIsolationThreadTimeoutInMilliseconds));
        }
    }

}
