package security.authentication.chain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static security.authentication.chain.Config.PASSWORD;
import static security.authentication.chain.Config.USERNAME;

@AutoConfigureMockMvc
@SpringBootTest(classes = {PersonController.class, Config.class})
class ChainTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void authorized() throws Exception {
        var authHeader = "Basic " + Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
        mvc.perform(get("/person").header("Authorization", authHeader))
                .andExpect(status().isOk())
                .andExpect(content().string("John"));
    }

    @Test
    void unauthorized() throws Exception {
        mvc.perform(get("/person"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void insecure() throws Exception {
        mvc.perform(get("/insecure"))
                .andExpect(status().isOk())
                .andExpect(content().string("InsecureData"));
    }

}