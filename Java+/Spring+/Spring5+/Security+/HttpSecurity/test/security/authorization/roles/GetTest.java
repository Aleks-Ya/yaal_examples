package security.authorization.roles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import security.authorization.roles.config.authentication.AuthenticationConfig;
import security.authorization.roles.config.authorization.AuthorizationConfig;
import security.authorization.roles.config.authorization.Roles;
import security.authorization.roles.controller.MainController;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static security.authorization.roles.config.authentication.UserCredentials.Admin;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuthenticationConfig.class, AuthorizationConfig.class, MainController.class})
@WebAppConfiguration
public class GetTest {

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
    void unauthorized() throws Exception {
        mvc.perform(get("/admin").with(anonymous()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void success() throws Exception {
        mvc.perform(get("/admin").with(user(Admin.LOGIN).roles(Roles.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(MainController.ADMIN_BODY));
    }

    @Test
    void testAnonymous() throws Exception {
        mvc.perform(get("/anonymous").with(anonymous()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(MainController.ANONYMOUS_BODY));
    }
}
