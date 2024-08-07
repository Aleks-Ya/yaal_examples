package security.authentication.session;

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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static security.authentication.session.SessionController.ENDPOINT;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MvcConfig.class, SecurityConfig.class, SessionController.class})
@WebAppConfiguration
class SessionControllerTest {
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
        var session = new MockHttpSession();
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
        var session = new MockHttpSession();
        var sessionId = session.getId();

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
        var session1 = new MockHttpSession();
        mvc.perform(get(ENDPOINT).session(session1).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(session1.getId())));

        var session2 = new MockHttpSession();
        mvc.perform(get(ENDPOINT).session(session2).with(user(USER)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(session2.getId())));
    }
}