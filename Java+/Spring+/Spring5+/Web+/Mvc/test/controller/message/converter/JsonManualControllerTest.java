package controller.message.converter;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Treat the body as JSON using @RequestBody and manual converting to JSON.
 */
@ContextConfiguration(classes = JsonManualController.class)
class JsonManualControllerTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        mvc.perform(
                post(JsonManualController.ENDPOINT)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("{ \"name\": \"abc\"}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("response=abc"));
    }
}