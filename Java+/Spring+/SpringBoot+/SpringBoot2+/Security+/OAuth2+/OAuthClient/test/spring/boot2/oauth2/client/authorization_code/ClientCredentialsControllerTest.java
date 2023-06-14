package spring.boot2.oauth2.client.authorization_code;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ClientCredentialsController.class, SecurityConfig.class},
        properties = "spring.config.location=classpath:application.yaml")
@AutoConfigureMockMvc
class ClientCredentialsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void authorized() throws Exception {
        var authHeader = "Basic " + Base64.getEncoder().encodeToString("user1:pass1".getBytes());
        mvc.perform(get("/person").header("Authorization", authHeader))
                .andExpect(status().isOk())
                .andExpect(content().string("John"));
    }

    @Test
    void unauthorized() throws Exception {
        mvc.perform(get("/clientCredentials/resourceServer/manual")).andExpect(status().isUnauthorized());
    }
}