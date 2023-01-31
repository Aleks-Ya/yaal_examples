package springdoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void customEndpointShouldBeAvailable() throws Exception {
        mvc.perform(get("/custom")).andExpect(status().isOk());
    }

    @Test
    void upperCaseEndpointShouldBeAvailable() throws Exception {
        mvc.perform(get("/upperCase/abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("ABC"));
    }
}