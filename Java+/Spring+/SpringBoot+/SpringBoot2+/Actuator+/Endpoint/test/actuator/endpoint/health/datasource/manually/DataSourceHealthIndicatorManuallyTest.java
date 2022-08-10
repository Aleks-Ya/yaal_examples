package actuator.endpoint.health.datasource.manually;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties =
        "spring.config.location=classpath:actuator/endpoint/health/datasource/manually/application.yaml")
@AutoConfigureMockMvc
class DataSourceHealthIndicatorManuallyTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusDetails() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.dataSource.status").value("UP"))
                .andExpect(jsonPath("$.components.dataSource.details.database").value("H2"));
    }
}
