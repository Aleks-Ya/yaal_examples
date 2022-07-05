package security.authentication.session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static security.authentication.session.SessionController.ENDPOINT;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MvcConfig.class, SecurityConfig.class, SessionController.class})
@WebAppConfiguration
public class SessionControllerTest {
    private static final User USER = new User(SecurityConfig.USERNAME, SecurityConfig.PASSWORD, Collections.emptyList());
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

    /**
     * A new session is created for each request.
     */
    @Test
    void differentSessionsAuto() throws Exception {
        mvc.perform(get(ENDPOINT).with(user(USER)))
                .andExpect(status().isOk());
        mvc.perform(get(ENDPOINT).with(user(USER)))
                .andExpect(status().isOk());
    }

    /**
     * Several requests using the same session.
     */
    @Test
    void theSameSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        mvc.perform(get(ENDPOINT).session(session).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(session.getId()));
        mvc.perform(get(ENDPOINT).session(session).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(session.getId()));
    }

    /**
     * New session is created after the current session invalidation.
     */
    @Test
    void createNewSessionAfterInvalidation() throws Exception {
        MockHttpSession session = new MockHttpSession();
        String sessionId = session.getId();

        mvc.perform(get(ENDPOINT).session(session).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(sessionId));

        session.invalidate();
        mvc.perform(get(ENDPOINT).session(session).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(not(equalTo(sessionId))));
    }

    /**
     * Use different session for several requests.
     */
    @Test
    void differentSessionsMock() throws Exception {
        MockHttpSession session1 = new MockHttpSession();
        mvc.perform(get(ENDPOINT).session(session1).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(session1.getId())));

        MockHttpSession session2 = new MockHttpSession();
        mvc.perform(get(ENDPOINT).session(session2).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(session2.getId())));
    }
}