package ru.yaal.spring.security.application.config.authentication.manager;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import ru.yaal.spring.security.application.config.authentication.AuthenticationProfiles;
import ru.yaal.spring.security.application.config.authentication.Roles;
import ru.yaal.spring.security.application.config.authentication.UserCredentials;

@Configuration
@Profile(AuthenticationProfiles.AUTHENTICATION_MANAGER_BUILDER)
class AuthenticationManagerBuilderConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(UserCredentials.User.LOGIN).password(UserCredentials.User.PASSWORD)
				.roles(Roles.USER);
		auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().withUser(UserCredentials.User.LOGIN)
				.password(UserCredentials.User.PASSWORD).roles(Roles.USER).and().withUser(UserCredentials.Admin.LOGIN)
				.password(UserCredentials.Admin.PASSWORD).roles(Roles.ADMIN);
	}
}
