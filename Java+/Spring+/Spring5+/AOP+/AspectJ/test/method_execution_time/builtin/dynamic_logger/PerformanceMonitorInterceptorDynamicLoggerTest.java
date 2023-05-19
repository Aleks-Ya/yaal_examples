package method_execution_time.builtin.dynamic_logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import util.InputStreamUtil;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProfilerConfig.class, PersonService.class})
@TestPropertySource(properties = "pointcut=execution(* method_execution_time.builtin.dynamic_logger.PersonService.*(..))")
class PerformanceMonitorInterceptorDynamicLoggerTest {
    @Autowired
    private PersonService personService;

    @Test
    void test() throws InterruptedException {
        try (var out = InputStreamUtil.redirectStdErr()) {
            System.setProperty("org.slf4j.simpleLogger.log." + PersonService.class.getName(), "TRACE");
            var person = new Person("John", "Mark");
            var fullName = personService.getFullName(person);
            assertThat(fullName).isEqualTo("John Mark");
            assertThat(out.toString())
                    .contains("[Test worker] TRACE method_execution_time.builtin.dynamic_logger.PersonService - " +
                            "StopWatch 'method_execution_time.builtin.dynamic_logger.PersonService.getFullName': running time =");
        }
    }
}
