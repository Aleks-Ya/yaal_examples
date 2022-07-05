package security.authentication.userdetails.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, SecurityConfig.class})
public class JdbcUserDetailsManagerTest {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailsManager userDetailsManager;

    @Test
    void userDetailsService() {
        UserDetails user = userDetailsService.loadUserByUsername(SecurityConfig.USERNAME);
        assertNotNull(user);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        GrantedAuthority expAuthority = new SimpleGrantedAuthority(SecurityConfig.ROLE);
        assertThat(authorities, contains(expAuthority));
    }

    @Test
    void userDetailsManager() {
        //Create an user
        String username = "Mary";
        GrantedAuthority expAuthority = new SimpleGrantedAuthority("readonly");
        UserDetails expUser = new User(username, "bird", Collections.singletonList(expAuthority));
        userDetailsManager.createUser(expUser);

        //Read the user
        UserDetails actUser = userDetailsService.loadUserByUsername(username);
        assertThat(actUser).isEqualTo(expUser));
        Collection<? extends GrantedAuthority> authorities = actUser.getAuthorities();
        assertThat(authorities, contains(expAuthority));
    }
}