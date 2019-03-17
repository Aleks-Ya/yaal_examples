package security.application.config.authentication.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import security.application.config.authentication.AuthenticationProfiles;
import security.application.config.authentication.Roles;
import security.application.config.authentication.UserCredentials.Admin;
import security.application.config.authentication.UserCredentials.User;

@Configuration
@SuppressWarnings("unused")
@Profile(AuthenticationProfiles.IN_MEMORY)
public class InMemoryConfig {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User.LOGIN).password(User.PASSWORD).roles(Roles.USER)
                .and()
                .withUser(Admin.LOGIN).password(Admin.PASSWORD).roles(Roles.USER, Roles.ADMIN);
    }
}
