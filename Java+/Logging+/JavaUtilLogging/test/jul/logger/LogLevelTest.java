package jul.logger;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static jul.Helper.loadConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class LogLevelTest {

    @Test
    public void defaultLogLevel() {
        loadConfig("jul/logger/LogLevelTest.properties");

        Logger rootLog = Logger.getLogger("");
        assertThat(rootLog.getLevel(), equalTo(Level.SEVERE));

        Logger log = Logger.getLogger("any.logger");
        assertThat(log.getLevel(), nullValue());//Level is inherited from parent
        Logger parent = log.getParent();
        assertThat(parent, equalTo(rootLog));
    }

    @Test
    public void specificLoggerLevel() {
        loadConfig("jul/logger/LogLevelTest.properties");

        Logger log = Logger.getLogger("a.b.c");
        assertThat(log.getLevel(), equalTo(Level.FINEST));
    }

}
