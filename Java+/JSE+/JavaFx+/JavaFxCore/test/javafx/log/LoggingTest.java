package javafx.log;

import com.sun.javafx.logging.PlatformLogger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DOES NOT WORK
 */
class LoggingTest extends ApplicationTest {
    static {
        System.setProperty("java.util.logging.ConsoleHandler.level", "FINE");
        System.setProperty("java.util.logging." + Scene.class.getName() + " .level", "FINE");
    }

    public LoggingTest() {
        Logger logger = Logger.getLogger(Scene.class.getName());
        logger.setLevel(Level.FINEST);
    }

    @Override
    public void start(Stage stage) {
        var logName = Scene.class.getName();
        PlatformLogger.getLogger(logName).warning("WARNING!!!");
        PlatformLogger.getLogger(logName).fine("FINE!!!");
        PlatformLogger.getLogger(logName).finer("FINER!!!");
        PlatformLogger.getLogger(logName).finest("FINEST!!!");
    }

    @Test
    void shouldContainComboBox() {
        var logName = Scene.class.getName();
        PlatformLogger.getLogger(logName).warning("WARNING!!!");
        PlatformLogger.getLogger(logName).fine("FINE!!!");
        PlatformLogger.getLogger(logName).finer("FINER!!!");
        PlatformLogger.getLogger(logName).finest("FINEST!!!");
    }
}
