package htmlunit.vtb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

class Config {
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String AUTH_COOKIE_PROPERTY = "auth.cookie";
    private static final String SLB_COOKIE_PROPERTY = "slb.cookie";
    private final File configFile;
    private final Properties config;

    Config() {
        this(new File(System.getProperty("user.home"), ".vtb-broker-report-downloader/config.properties"));
    }

    Config(File configFile) {
        this.configFile = configFile;
        config = readConfig();
    }

    String getUsername() {
        return config.getProperty(USERNAME_PROPERTY);
    }

    void setUsername(String username) {
        writeProperty(USERNAME_PROPERTY, username);
    }

    String getPassword() {
        return config.getProperty(PASSWORD_PROPERTY);
    }

    void setPassword(String password) {
        writeProperty(PASSWORD_PROPERTY, password);
    }

    Optional<AuthData> getAuthData() {
        var authCookie = config.getProperty(AUTH_COOKIE_PROPERTY);
        var slbCookie = config.getProperty(SLB_COOKIE_PROPERTY);
        if (authCookie == null || slbCookie == null) {
            return Optional.empty();
        } else {
            return Optional.of(new AuthData(authCookie, slbCookie));
        }
    }

    void setAuthData(AuthData authData) {
        writeProperty(AUTH_COOKIE_PROPERTY, authData.getAuthCookie());
        writeProperty(SLB_COOKIE_PROPERTY, authData.getSlbCookie());
    }

    private Properties readConfig() {
        try {
            var config = new Properties();
            if (configFile.exists()) {
                config.load(new FileInputStream(configFile));
            }
            return config;
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }

    void writeProperty(String propertyName, String propertyValue) {
        try {
            var props = readConfig();
            props.setProperty(propertyName, propertyValue);
            props.store(new FileOutputStream(configFile), "Saved " + LocalDateTime.now());
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }

    static class ConfigException extends RuntimeException {
        public ConfigException(Throwable cause) {
            super(cause);
        }
    }
}
