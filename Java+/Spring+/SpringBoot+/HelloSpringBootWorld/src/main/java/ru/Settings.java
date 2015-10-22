package ru;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "configuration")
public class Settings {
    @NotEmpty
    private String message;

    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public void setMessage(String message) {
        this.message = message;
    }
}
