package security.authentication.password.encoder;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.List;

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
    static final String USERNAME = "the_user";
    static final String PASSWORD = "the_password";
    private static final String ENCODED_PASSWORD = "7ca95240d57b1a3477bf0673cbaa69a8edcb1a36173e7a1b04351652104c56504d1c939f7604a61d";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return inMemoryUserDetailsService();
    }

    /**
     * Salt is generated automatically
     * in {@link StandardPasswordEncoder#matches(java.lang.CharSequence, java.lang.String)}.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    UserDetailsService inMemoryUserDetailsService() {
        User user = new User(USERNAME, ENCODED_PASSWORD, Collections.emptyList());
        List<UserDetails> users = Collections.singletonList(user);
        return new InMemoryUserDetailsManager(users);

    }
}
