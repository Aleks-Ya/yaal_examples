package controller.request.body;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Treat the body as JSON using @RequestBody and auto converting to JSON.
 */
@ContextConfiguration(classes = {JsonAutoController.class, JsonAutoControllerTest.Config.class})
class JsonAutoControllerTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        mvc.perform(
                        post(JsonAutoController.ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"name\": \"abc\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("response=abc"));
    }

    @EnableWebMvc
    @Configuration
    static class Config {
    }
}