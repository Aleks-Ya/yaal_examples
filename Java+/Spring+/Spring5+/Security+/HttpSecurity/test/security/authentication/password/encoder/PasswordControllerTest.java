package security.authentication.password.encoder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityConfig.class, DataSourceConfig.class})
class PasswordControllerTest {

    private static final List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("admin"));
    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void test() throws Exception {
        var username = "aleks";
        var rawPassword = "apass";

        var encodedPass = passwordEncoder.encode(rawPassword);

        userDetailsManager.createUser(new User(username, encodedPass, authorities));

        var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery("select * from users where username='" + username + "'");
        resultSet.next();
        var actPassword = resultSet.getString(2);
        System.out.println("Stored pass: " + actPassword);
        assertThat(actPassword).isEqualTo(encodedPass);

        var actUser = userDetailsManager.loadUserByUsername(username);
        var actPass2 = actUser.getPassword();
        assertThat(passwordEncoder.matches(rawPassword, actPass2)).isTrue();
    }

    @Test
    void differSaltForTwoUsers() {
        var username1 = "john";
        var username2 = "mary";
        var rawPassword = "pass";

        var encodedPass1 = passwordEncoder.encode(rawPassword);
        var encodedPass2 = passwordEncoder.encode(rawPassword);
        assertThat(encodedPass1).isNotEqualTo(encodedPass2);

        userDetailsManager.createUser(new User(username1, encodedPass1, authorities));
        userDetailsManager.createUser(new User(username2, encodedPass2, authorities));

        var actUser1 = userDetailsManager.loadUserByUsername(username1);
        assertThat(passwordEncoder.matches(rawPassword, actUser1.getPassword())).isTrue();

        var actUser2 = userDetailsManager.loadUserByUsername(username1);
        assertThat(passwordEncoder.matches(rawPassword, actUser2.getPassword())).isTrue();
    }
}