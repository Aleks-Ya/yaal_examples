package gptui.model.config;

import jakarta.inject.Singleton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Singleton
class ConfigModelImpl implements ConfigModel {
    private final Properties properties = new Properties();

    public ConfigModelImpl() {
        try {
            var configPath = Path.of(System.getProperty("user.home"), ".gpt", "config.properties");
            properties.load(Files.newInputStream(configPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
