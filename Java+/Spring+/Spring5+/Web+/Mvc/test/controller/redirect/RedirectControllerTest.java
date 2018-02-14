package controller.redirect;

import common.BaseTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Send redirect from Controller.
 */
@ContextConfiguration(classes = RedirectController.class)
public class RedirectControllerTest extends BaseTest {

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