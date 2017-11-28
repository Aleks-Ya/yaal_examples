package security.authorization.roles.config.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import security.authorization.roles.config.authentication.Roles;

@EnableWebMvc
@EnableWebSecurity
public class AuthorizationConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
                .mvcMatchers("/anonymous").anonymous()
                .mvcMatchers("/admin").hasRole(Roles.ADMIN)
				.mvcMatchers("/user").hasAnyRole(Roles.USER, Roles.ADMIN)
                .anyRequest().authenticated().and().formLogin();
	}
}
