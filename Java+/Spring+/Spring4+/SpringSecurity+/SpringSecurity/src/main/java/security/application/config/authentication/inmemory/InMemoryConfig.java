package security.application.config.authentication.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import security.application.config.authentication.AuthenticationProfiles;
import security.application.config.authentication.Roles;
import security.application.config.authentication.UserCredentials;

@Configuration
@Profile(AuthenticationProfiles.IN_MEMORY)
public class InMemoryConfig {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(UserCredentials.User.LOGIN).password(UserCredentials.User.PASSWORD).roles(Roles.USER)
                .and()
                .withUser(UserCredentials.Admin.LOGIN).password(UserCredentials.Admin.PASSWORD).roles(Roles.USER, Roles.ADMIN);
    }
}
