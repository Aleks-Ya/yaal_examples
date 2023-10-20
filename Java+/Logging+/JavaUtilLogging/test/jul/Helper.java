package jul;

import java.io.IOException;
import java.util.logging.LogManager;

public class Helper {
    public static void loadConfig(String resource) {
        try {
            var configIs = Helper.class.getClassLoader().getResourceAsStream(resource);
            LogManager.getLogManager().readConfiguration(configIs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
