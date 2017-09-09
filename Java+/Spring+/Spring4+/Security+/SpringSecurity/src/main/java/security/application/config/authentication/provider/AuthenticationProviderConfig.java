package security.application.config.authentication.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import security.application.config.authentication.AuthenticationProfiles;

@Configuration
@SuppressWarnings("unused")
@Profile(AuthenticationProfiles.AUTHENTICATION_PROVIDER)
class AuthenticationProviderConfig {
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }
}
