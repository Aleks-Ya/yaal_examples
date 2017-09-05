package application.config.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class AuthenticationConfig {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(UserCredentials.User.LOGIN).password(UserCredentials.User.PASSWORD).roles(Roles.USER)
//                .and()
//                .withUser(UserCredentials.Admin.LOGIN).password(UserCredentials.Admin.PASSWORD).roles(Roles.USER, Roles.ADMIN);
    }
}
