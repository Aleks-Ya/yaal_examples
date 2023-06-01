package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * The main class. Choose a authentication type.
 */
@SpringBootApplication
@EnableOAuth2Sso
public class SecurityApplication {
    public static void main(String[] args) throws Exception {
        System.setProperty("server.port", "8090");
        SpringApplication.run(SecurityApplication.class, args);
    }
}