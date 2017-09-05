package application;

import application.config.authentication.UserCredentials;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class CsrfShowcaseTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void unauthorized() throws Exception {
        mvc.perform(post("/admin")).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void success() throws Exception {
        mvc.perform(
                post("/admin")
                        .with(user(UserCredentials.Admin.LOGIN).password(UserCredentials.Admin.PASSWORD))
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


    @Test
    public void testAnonymous() throws Exception {
        mvc.perform(post("/anonymous").with(anonymous()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
