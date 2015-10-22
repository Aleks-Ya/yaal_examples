package ru;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "configuration")
public class Settings {
    private String message;

    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public void setMessage(String message) {
        this.message = message;
    }
}
