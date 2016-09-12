package ru.yaal.spring.security.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.yaal.spring.security.application.config.authentication.AuthenticationProfiles;

/**
 * The main class. Choose a authentication type.
 */
@SpringBootApplication
public class SecurityApplication {
	public static void main(String[] args) throws Exception {
		System.setProperty("spring.profiles.active", AuthenticationProfiles.USER_DETAIL_SERVICE);
		SpringApplication.run(SecurityApplication.class, args);
	}
}