package security.authentication.userdetails.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static security.authentication.userdetails.jdbc.JdbcUserDetailsManagerController.ENDPOINT;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class, MvcConfig.class, SecurityConfig.class,
        JdbcUserDetailsManagerController.class})
@WebAppConfiguration
public class JdbcUserDetailsManagerControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void successAuthentication() throws Exception {
        String correctPassword = SecurityConfig.PASSWORD;
        Authentication auth = new UsernamePasswordAuthenticationToken(SecurityConfig.USERNAME, correctPassword);
        mvc.perform(get(ENDPOINT).with(authentication(auth)))
                .andExpect(status().isOk());
    }

    @Test
    void failAuthentication() throws Exception {
        String wrongPassword = SecurityConfig.PASSWORD + "a";
        Authentication auth = new UsernamePasswordAuthenticationToken(SecurityConfig.USERNAME, wrongPassword);
        mvc.perform(get(ENDPOINT).with(authentication(auth)))
                .andExpect(status().isForbidden());
    }

}