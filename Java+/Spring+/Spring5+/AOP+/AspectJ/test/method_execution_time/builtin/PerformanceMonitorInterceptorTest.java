package method_execution_time.builtin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Using standard Spring class PerformanceMonitorInterceptor for measuring method execution time.
 * <p>
 * Source: https://www.baeldung.com/spring-performance-logging
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProfilerConfig.class, PersonService.class})
@TestPropertySource(properties = "pointcut=execution(* method_execution_time.builtin.PersonService.*(..))")
class PerformanceMonitorInterceptorTest {

    @Autowired
    private PersonService personService;

    @Test
    void test() throws InterruptedException {
        System.setProperty("org.slf4j.simpleLogger.log.method_execution_time.builtin.PersonService", "TRACE");
        var person = new Person("John", "Mark");
        var fullName = personService.getFullName(person);
        assertThat(fullName).isEqualTo("John Mark");
    }
}
