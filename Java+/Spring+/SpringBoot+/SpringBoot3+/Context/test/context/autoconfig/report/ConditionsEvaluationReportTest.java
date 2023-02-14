package context.autoconfig.report;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Print "CONDITIONS EVALUATION REPORT" to log.
 */
@SpringBootTest(webEnvironment = NONE,
        properties = "spring.config.location=classpath:context/autoconfig/report/application.yaml")
@SpringBootConfiguration
@EnableAutoConfiguration
class ConditionsEvaluationReportTest {
    @Test
    void report() {
    }
}