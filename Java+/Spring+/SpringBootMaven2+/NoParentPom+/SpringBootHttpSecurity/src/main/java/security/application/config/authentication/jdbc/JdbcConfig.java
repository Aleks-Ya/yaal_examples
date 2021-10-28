package security.application.config.authentication.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.util.InMemoryResource;
import security.application.config.authentication.AuthenticationProfiles;
import security.application.config.authentication.Roles;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * JDBC Authentication.
 * Users already exists in DataSource.
 */
@Configuration
@SuppressWarnings("unused")
@Profile(AuthenticationProfiles.JDBC)
class JdbcConfig {

    @Autowired
    private DataSource usersDs;

    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(usersDs);
    }

    @Bean
    public DataSource users() throws NamingException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).build();
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("org/springframework/security/core/userdetails/jdbc/users.ddl"));
        populator.addScript(new InMemoryResource("INSERT INTO users(username, password, enabled) VALUES ('jdbc', 'jdbc', true)"));
        populator.addScript(new InMemoryResource(String.format("INSERT INTO authorities(username, authority) VALUES ('jdbc', '%s')", Roles.USER)));
        populator.execute(db);
        return db;
    }
}
