package with.user.details;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.List;

@Configuration
@Import(HelloMessageService.class)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class TestConfig {
    static final String USERNAME = "name";
    static final String USER_DETAILS_SERVICE_BEAN = "testUserDetailService";

    @Bean(name = USER_DETAILS_SERVICE_BEAN)
    public UserDetailsService testUserDetailService() {
        List<UserDetails> users = Collections.singletonList(new User(USERNAME, "pass", Collections.emptyList()));
        return new InMemoryUserDetailsManager(users);
    }
}
