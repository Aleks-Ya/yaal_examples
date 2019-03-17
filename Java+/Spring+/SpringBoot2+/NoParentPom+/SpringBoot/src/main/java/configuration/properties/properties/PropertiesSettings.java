package configuration.properties.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings({"WeakerAccess", "unused"})
@ConfigurationProperties(prefix = "configuration")
class PropertiesSettings {
    private String message;
    private System system;

    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public void setMessage(String message) {
        this.message = message;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public static class System {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
