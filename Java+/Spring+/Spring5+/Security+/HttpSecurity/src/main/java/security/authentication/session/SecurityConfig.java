package security.authentication.session;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
    static final String USERNAME = "the_user";
    static final String PASSWORD = "the_password";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().sessionManagement()
                .maximumSessions(1).and()
                .sessionFixation().migrateSession();
    }
}
