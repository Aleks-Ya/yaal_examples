package security.authentication.rememberme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static security.authentication.rememberme.RememberMeController.ENDPOINT;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcConfig.class, SecurityConfig.class, RememberMeController.class})
@WebAppConfiguration
class RememberMeTest {
    private static final User USER = new User(SecurityConfig.USERNAME, SecurityConfig.PASSWORD, Collections.emptyList());
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
    void notAuthorizedRequestIsForbidden() throws Exception {
        mvc.perform(get(ENDPOINT).with(user(USER))).andExpect(status().isOk());
        mvc.perform(get(ENDPOINT)).andExpect(status().isForbidden());
    }

    @Test
    void authorizationViaSession() throws Exception {
        var session = new MockHttpSession();
        mvc.perform(get(ENDPOINT)
                        .session(session)
                        .with(user(USER))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(session.getId()));

        mvc.perform(get(ENDPOINT)
                        .session(session)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(session.getId()));
    }

    @Test
    void rememberMe() throws Exception {
        var body = "";
        var session1 = new MockHttpSession();
        var mvcResult = mvc.perform(post(ENDPOINT)
                        .content(body)
                        .session(session1)
                        .with(user(USER))
                        .param("remember-me", "on")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(session1.getId()))
                .andReturn();
        var cookies = mvcResult.getResponse().getCookies();
//
//        assertThat(cookies, not(emptyArray()));

        var session2 = new MockHttpSession();
        mvc.perform(get(ENDPOINT)
                                .session(session2)
//                .cookie(cookies)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(session2.getId()));
    }


}