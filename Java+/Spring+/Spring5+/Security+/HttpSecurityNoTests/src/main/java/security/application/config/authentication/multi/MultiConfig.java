package security.application.config.authentication.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import security.application.config.authentication.AuthenticationProfiles;
import security.application.config.authentication.Roles;
import security.application.config.authentication.UserCredentials.Admin;
import security.application.config.authentication.UserCredentials.User;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Authentication using multi authentication methods.
 */
@Configuration
@SuppressWarnings("unused")
@Profile(AuthenticationProfiles.MULTI)
class MultiConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(User.LOGIN).password(User.PASSWORD).roles(Roles.USER);
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
                .withUser(User.LOGIN).password(User.PASSWORD).roles(Roles.USER)
                .and()
                .withUser(Admin.LOGIN).password(Admin.PASSWORD).roles(Roles.ADMIN);
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }
}
