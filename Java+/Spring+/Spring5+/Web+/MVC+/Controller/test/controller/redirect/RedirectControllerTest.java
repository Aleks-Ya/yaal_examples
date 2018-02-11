package controller.redirect;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Send redirect from Controller.
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedirectController.class)
public class RedirectControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void requestParam() throws Exception {
        String message = "hello";
        MvcResult mvcResult = mvc.perform(get(RedirectController.ENDPOINT_1).param("message", message))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(RedirectController.ENDPOINT_2 + "?message=" + message))
                .andReturn();

        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        mvc.perform(get(redirectedUrl))
                .andExpect(status().isOk())
                .andExpect(content().string("Second endpoint received message " + message));
    }
}