package security.application.config.authentication.jdbc_default_schema;

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
 * JDBC Authentication.
 * The database is populated by JdbcUserDetailsManagerConfigurer.
 */
@SuppressWarnings("unused")
@Configuration
@Profile(AuthenticationProfiles.JDBC_DEFAULT_SCHEMA)
class JdbcDefaultSchemaConfig {

    @Autowired
    private DataSource emptyDs;

    @Autowired
    public void jdbc(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(emptyDs).withDefaultSchema()
                .withUser(User.LOGIN).password(User.PASSWORD).roles(Roles.USER)
                .and()
                .withUser(Admin.LOGIN).password(Admin.PASSWORD).roles(Roles.ADMIN);
    }

    @Bean
    public DataSource empty() throws NamingException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

}
