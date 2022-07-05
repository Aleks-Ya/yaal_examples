package security.application.config.authentication;

public interface UserCredentials {
    interface User {
        String LOGIN = "u";
        String PASSWORD = "p";
    }

    interface Admin {
        String LOGIN = "a";
        String PASSWORD = "p";
    }
}
