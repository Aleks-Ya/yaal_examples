package cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PersonController.class)
@CucumberContextConfiguration
@AutoConfigureMockMvc
public class SpringContextConfiguration {
}