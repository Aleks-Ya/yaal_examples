package ru.yaal.spring.security.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;

import ru.yaal.spring.security.application.Profiles;

@Configuration
@Profile(Profiles.AUTHENTICATION_PROVIDER)
public class AuthenticationProviderSecurityConfig {
	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider();
	}
}
