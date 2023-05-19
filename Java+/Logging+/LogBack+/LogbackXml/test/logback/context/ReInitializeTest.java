package logback.context;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reinitialize LogBack from another XML file.
 */
class ReInitializeTest extends BaseLogbackTest {
    @Test
    void reinitialize() {
        try (var stdOut1 = reinitialize("logback/context/ReInitializeTest_old.xml")) {
            var oldLog = LoggerFactory.getLogger(ReInitializeTest.class);
            oldLog.info("Hi OLD");
            assertThat(stdOut1).hasToString("OLD CONFIG: Hi OLD\n");

            var stdOut2 = reinitialize("logback/context/ReInitializeTest_new.xml");

            var newLog = LoggerFactory.getLogger(ReInitializeTest.class);
            newLog.info("Hi NEW");
            assertThat(stdOut2).hasToString("NEW CONFIG: Hi NEW\n");
        }
    }

//    @SuppressWarnings("SameParameterValue")
//    private void reinitializeLogback(String resource) {
//        try (var is = ReInitializeTest.class.getClassLoader().getResourceAsStream(resource)) {
//            var loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//            loggerContext.reset();
//            var configurator = new JoranConfigurator();
//            configurator.setContext(loggerContext);
//            configurator.doConfigure(is);
//        } catch (JoranException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
