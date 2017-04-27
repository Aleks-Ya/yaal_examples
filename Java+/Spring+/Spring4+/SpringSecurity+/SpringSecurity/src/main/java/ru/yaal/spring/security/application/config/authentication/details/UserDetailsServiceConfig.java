package ru.yaal.spring.security.application.config.authentication.details;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import ru.yaal.spring.security.application.config.authentication.AuthenticationProfiles;

@Configuration
@Profile(AuthenticationProfiles.USER_DETAIL_SERVICE)
public class UserDetailsServiceConfig {
	@Bean
	public UserDetailsService springDataUserDetailsService() {
		Collection<GrantedAuthority> authorities = Collections.emptyList();
		UserDetails user = new User("us", "pa", authorities);
		Collection<UserDetails> users = Arrays.asList(user);
		return new InMemoryUserDetailsManager(users);
	}
}
