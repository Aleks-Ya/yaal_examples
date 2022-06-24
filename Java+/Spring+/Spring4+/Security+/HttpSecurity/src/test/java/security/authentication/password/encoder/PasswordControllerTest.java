package security.authentication.password.encoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SecurityConfig.class, DataSourceConfig.class})
public class PasswordControllerTest {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("admin"));

    @Test
    void test() throws Exception {
        String username = "aleks";
        String rawPassword = "apass";

        String encodedPass = passwordEncoder.encode(rawPassword);

        userDetailsManager.createUser(new User(username, encodedPass, authorities));

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where username='" + username + "'");
        resultSet.next();
        String actPassword = resultSet.getString(2);
        System.out.println("Stored pass: " + actPassword);
        assertEquals(encodedPass, actPassword);

        UserDetails actUser = userDetailsManager.loadUserByUsername(username);
        String actPass2 = actUser.getPassword();
        assertTrue(passwordEncoder.matches(rawPassword, actPass2));
    }

    @Test
    void differSaltForTwoUsers() {
        String username1 = "john";
        String username2 = "mary";
        String rawPassword = "pass";

        String encodedPass1 = passwordEncoder.encode(rawPassword);
        String encodedPass2 = passwordEncoder.encode(rawPassword);
        assertNotEquals(encodedPass1, encodedPass2);

        userDetailsManager.createUser(new User(username1, encodedPass1, authorities));
        userDetailsManager.createUser(new User(username2, encodedPass2, authorities));

        UserDetails actUser1 = userDetailsManager.loadUserByUsername(username1);
        assertTrue(passwordEncoder.matches(rawPassword, actUser1.getPassword()));

        UserDetails actUser2 = userDetailsManager.loadUserByUsername(username1);
        assertTrue(passwordEncoder.matches(rawPassword, actUser2.getPassword()));
    }
}