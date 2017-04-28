package security.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import security.application.config.authentication.AuthenticationProfiles;

/**
 * The main class. Choose a authentication type.
 */
@SpringBootApplication
public class SecurityApplication {
	public static void main(String[] args) throws Exception {
		System.setProperty("spring.profiles.active", AuthenticationProfiles.AUTHENTICATION_MANAGER_BUILDER);
		SpringApplication.run(SecurityApplication.class, args);
	}
}