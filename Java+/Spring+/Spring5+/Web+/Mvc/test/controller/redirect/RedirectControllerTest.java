package controller.redirect;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Send redirect from Controller.
 */
@ContextConfiguration(classes = RedirectController.class)
class RedirectControllerTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        var message = "hello";
        var mvcResult = mvc.perform(get(RedirectController.ENDPOINT_1).param("message", message))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(RedirectController.ENDPOINT_2 + "?message=" + message))
                .andReturn();

        var redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        mvc.perform(get(redirectedUrl))
                .andExpect(status().isOk())
                .andExpect(content().string("Second endpoint received message " + message));
    }
}