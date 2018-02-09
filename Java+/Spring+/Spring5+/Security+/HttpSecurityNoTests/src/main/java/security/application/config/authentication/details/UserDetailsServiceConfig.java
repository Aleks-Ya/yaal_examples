package security.application.config.authentication.details;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import security.application.config.authentication.AuthenticationProfiles;

import java.util.Collection;
import java.util.Collections;

@Configuration
@SuppressWarnings("unused")
@Profile(AuthenticationProfiles.USER_DETAIL_SERVICE)
public class UserDetailsServiceConfig {
    @Bean
    public UserDetailsService springDataUserDetailsService() {
        Collection<GrantedAuthority> authorities = Collections.emptyList();
        UserDetails user = new User("us", "pa", authorities);
        Collection<UserDetails> users = Collections.singletonList(user);
        return new InMemoryUserDetailsManager(users);
    }
}
