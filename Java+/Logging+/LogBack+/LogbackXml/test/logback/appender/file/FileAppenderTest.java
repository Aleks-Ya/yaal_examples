package logback.appender.file;

import logback.BaseLogbackTest;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import util.FileUtil;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Конфигурирование и вывод в RollingFileAppender.
 */
class FileAppenderTest extends BaseLogbackTest {
    @Test
    void file() {
        var logFile = new File("/tmp/FileAppenderTest.log");
        FileUtil.deleteFileSilent(logFile);
        assertThat(logFile).doesNotExist();
        reinitialize("logback/appender/file/logback.xml");
        var log = LoggerFactory.getLogger(FileAppenderTest.class);
        log.info("Hi!");
        assertThat(logFile).hasContent("File: Hi!");
    }
}
