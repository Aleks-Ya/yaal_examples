package actuator.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AnonymousTest.Config.class,
        properties = "spring.config.location=classpath:actuator/security/Anonymous.yaml")
@AutoConfigureMockMvc
@EnableAutoConfiguration
class AnonymousTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void health() throws Exception {
        mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
    }

    @Configuration
    static class Config {
    }
}
