package jul.handler;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static jul.Helper.loadConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.emptyArray;

public class ConsoleHandlerTest {

    @Test
    void handler() {
        loadConfig("jul/handler/ConsoleHandlerTest.properties");

        Logger rootLog = Logger.getLogger("");
        assertThat(rootLog.getHandlers(), arrayWithSize(1));

        Logger log = Logger.getLogger(ConsoleHandlerTest.class.getName());
        assertThat(log.getHandlers(), emptyArray());

        log.warning("Hello, Логгер!");
    }
}
