package security.application.config.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import security.application.config.authentication.Roles;

@EnableWebSecurity
class AuthorizeSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().mvcMatchers("/anonymus").anonymous().mvcMatchers("/admin").hasRole(Roles.ADMIN)
				.mvcMatchers("/user").hasAnyRole(Roles.USER, Roles.ADMIN).anyRequest().authenticated().and()
				.formLogin();
	}
}
