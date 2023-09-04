package gptui;

import java.nio.file.Path;

public class Configuration {
    private static final Configuration instance = new Configuration();
    private final Path appDir = Path.of(System.getProperty("user.home"), ".gpt");

    private Configuration() {
    }

    public static Configuration getInstance() {
        return instance;
    }

    public Path getAppDir() {
        return appDir;
    }
}
