package actuator.endpoint.health.diskspace;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.config.location=classpath:actuator/endpoint/health/diskspace/application.yaml"})
@AutoConfigureMockMvc
class DiskSpaceHealthIndicatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusDetails() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.diskSpace.status").value("UP"))
                .andExpect(jsonPath("$.components.diskSpace.details.exists").value("true"))
                .andExpect(jsonPath("$.components.diskSpace.details.total").exists())
                .andExpect(jsonPath("$.components.diskSpace.details.free").exists())
                .andExpect(jsonPath("$.components.diskSpace.details.threshold").exists());
    }
}
