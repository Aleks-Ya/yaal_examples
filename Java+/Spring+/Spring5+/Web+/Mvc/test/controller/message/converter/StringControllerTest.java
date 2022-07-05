package controller.message.converter;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Treat the body as String using @RequestBody.
 */
@ContextConfiguration(classes = StringController.class)
class StringControllerTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        mvc.perform(
                        post(StringController.ENDPOINT)
                                .contentType("text/plain")
                                .content("the_body")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("body=the_body"));
    }
}