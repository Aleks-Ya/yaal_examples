package app.mvc;

import app.BaseMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HelloWorldControllerTest extends BaseMvcTest {

    @Test
    void hello() throws Exception {
        mvc.perform(
                get(HelloWorldController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(HelloWorldController.CONTENT));
    }
}