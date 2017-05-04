package storage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

public class FileStorage implements Storage {
    private final File file;

    public FileStorage(File file) {
        this.file = file;
    }

    private static String getAuthorizationCodePropertyName(String sessionId) {
        return sessionId + "_authorization_code";
    }

    private static String getAccessTokenPropertyName(String sessionId) {
        return sessionId + "_access_token";
    }

    private static String getRefreshTokenPropertyName(String sessionId) {
        return sessionId + "_refresh_token";
    }

    @Override
    public synchronized void saveAuthorizationCode(String sessionId, String authorizationCode) {
        storeProperty(getAuthorizationCodePropertyName(sessionId), authorizationCode);
    }

    @Override
    public synchronized void saveAccessToken(String sessionId, String accessToken) {
        storeProperty(getAccessTokenPropertyName(sessionId), accessToken);
    }

    @Override
    public synchronized void saveRefreshToken(String sessionId, String refreshToken) {
        storeProperty(getRefreshTokenPropertyName(sessionId), refreshToken);
    }

    @Override
    public synchronized String readRefreshToken(String sessionId) {
        return readProperty(getRefreshTokenPropertyName(sessionId));
    }

    @Override
    public synchronized String readAccessToken(String sessionId) {
        return readProperty(getAccessTokenPropertyName(sessionId));
    }

    @Override
    public synchronized String readAuthenticationCode(String sessionId) {
        return readProperty(getAuthorizationCodePropertyName(sessionId));
    }

    private void storeProperty(String propName, String value) {
        try {
            Properties props = new Properties();
            props.load(new FileReader(file));
            props.setProperty(propName, value);
            props.store(new FileWriter(file), LocalDateTime.now().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readProperty(String propName) {
        try {
            Properties props = new Properties();
            props.load(new FileReader(file));
            return props.getProperty(propName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
