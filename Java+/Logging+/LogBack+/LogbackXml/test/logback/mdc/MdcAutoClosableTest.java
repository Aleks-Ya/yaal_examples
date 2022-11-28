package logback.mdc;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

class MdcAutoClosableTest {
    @Test
    void mdc() {
        System.setProperty("logback.configurationFile", "logback/mdc/SimpleMdcTest.xml");
        var logger = LoggerFactory.getLogger(MdcAutoClosableTest.class);
        try (var ignored = new MdcAutoClosable(Map.of("first", "John"))) {
            logger.info("MDC works 1");
        }
        try (var ignored = new MdcAutoClosable(Map.of("last", "Smith"))) {
            logger.info("MDC works 2");
        }
    }

    static class MdcAutoClosable implements AutoCloseable {
        public MdcAutoClosable(Map<String, String> map) {
            map.forEach(MDC::put);
        }

        @Override
        public void close() {
            MDC.clear();
        }
    }
}
