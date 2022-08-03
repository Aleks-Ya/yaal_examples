package security.authentication.basic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test basic authentication to a controller.
 */
@SpringBootTest(classes = PersonController.class,
        properties = "spring.config.location=classpath:security/authentication/basic/application.yaml")
@AutoConfigureMockMvc
class BasicAuthTest {

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
        mvc.perform(get("/person"))
                .andExpect(status().isUnauthorized());
    }

}