package security.authorization.roles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import security.authorization.roles.config.authentication.AuthenticationConfig;
import security.authorization.roles.config.authentication.UserCredentials;
import security.authorization.roles.config.authorization.AuthorizationConfig;
import security.authorization.roles.config.authorization.Roles;
import security.authorization.roles.controller.MainController;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuthenticationConfig.class, AuthorizationConfig.class, MainController.class})
@WebAppConfiguration
public class PostTest {

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
        mvc.perform(post("/admin").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void success() throws Exception {
        mvc.perform(
                        post("/admin")
                                .with(user(UserCredentials.Admin.LOGIN).roles(Roles.ADMIN))
                                .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


    @Test
    void testAnonymous() throws Exception {
        mvc.perform(post("/anonymous").with(anonymous()).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(MainController.ANONYMOUS_BODY));
    }
}
