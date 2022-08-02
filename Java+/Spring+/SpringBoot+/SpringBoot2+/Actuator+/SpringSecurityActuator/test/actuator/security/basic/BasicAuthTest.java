package actuator.security.basic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO NOT WORK
@SpringBootTest(classes = BasicAuthTest.Config.class,
        properties = "spring.config.location=classpath:actuator/security/basic/BasicAuth.yaml")
@AutoConfigureMockMvc
class BasicAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthorized() throws Exception {
        mockMvc.perform(get("/actuator/health")).andExpect(status().isUnauthorized());
    }

    @Test
    void basicAuth() throws Exception {
        var credentials = Base64.getEncoder().encodeToString("user1:pass1".getBytes());
        var builder = get("/actuator/health")
                .header("Authorization", "Basic " + credentials);
        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Configuration
    static class Config {
    }
}
