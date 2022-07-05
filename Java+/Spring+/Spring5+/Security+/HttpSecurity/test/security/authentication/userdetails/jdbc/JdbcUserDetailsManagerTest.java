package security.authentication.userdetails.jdbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class, SecurityConfig.class})
class JdbcUserDetailsManagerTest {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailsManager userDetailsManager;

    @Test
    void userDetailsService() {
        var user = userDetailsService.loadUserByUsername(SecurityConfig.USERNAME);
        assertThat(user).isNotNull();

        var authorities = user.getAuthorities();
        GrantedAuthority expAuthority = new SimpleGrantedAuthority(SecurityConfig.ROLE);
        assertThat(authorities.contains(expAuthority)).isTrue();
    }

    @Test
    void userDetailsManager() {
        //Create an user
        var username = "Mary";
        GrantedAuthority expAuthority = new SimpleGrantedAuthority("readonly");
        UserDetails expUser = new User(username, "bird", Collections.singletonList(expAuthority));
        userDetailsManager.createUser(expUser);

        //Read the user
        var actUser = userDetailsService.loadUserByUsername(username);
        assertThat(actUser).isEqualTo(expUser);
        var authorities = actUser.getAuthorities();
        assertThat(authorities.contains(expAuthority)).isTrue();
    }
}