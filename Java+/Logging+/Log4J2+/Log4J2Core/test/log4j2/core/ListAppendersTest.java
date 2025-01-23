package log4j2.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListAppendersTest extends BaseLog4jTest {
    @Test
    void listAppenders() {
        var context = (LoggerContext) LogManager.getContext(false);
        var config = context.getConfiguration();
        var appenders = config.getAppenders();
        assertThat(appenders).isNotEmpty();
        for (var appender : appenders.values()) {
            System.out.printf("Appender: name=%s, class=%s, layout='%s'\n",
                    appender.getName(), appender.getClass().getSimpleName(), appender.getLayout());
        }
    }
}
