package security.authentication.rememberme;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import java.util.Collections;
import java.util.List;

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
    static final String USERNAME = "the_user";
    static final String PASSWORD = "the_password";
    private static final String KEY = "springRocks";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .rememberMe()
                .key(KEY)
                .rememberMeServices(tokenBasedRememberMeServices());
    }

    @Bean
    TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        var services = new TokenBasedRememberMeServices(KEY, userDetailsService());
//        services.setCookieName();
        return services;
    }

    @Bean
    RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(KEY);
    }

    @Bean
    RememberMeAuthenticationFilter rememberMeAuthenticationFilter() {
        return new RememberMeAuthenticationFilter(authenticationManager(), tokenBasedRememberMeServices());
    }

    @Override
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = Collections.singletonList(rememberMeAuthenticationProvider());
        return new ProviderManager(providers);
    }

    @Bean
    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
        var filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setRememberMeServices(tokenBasedRememberMeServices());
        return filter;
    }
}
