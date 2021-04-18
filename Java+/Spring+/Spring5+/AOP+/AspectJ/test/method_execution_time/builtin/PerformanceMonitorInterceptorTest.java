package method_execution_time.builtin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using standard Spring class PerformanceMonitorInterceptor for measuring method execution time.
 *
 * Source: https://www.baeldung.com/spring-performance-logging
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProfilerConfig.class, PersonService.class})
@TestPropertySource(properties = "pointcut=execution(* method_execution_time.builtin.PersonService.*(..))")
public class PerformanceMonitorInterceptorTest {

    @Autowired
    private PersonService personService;

    @Test
    public void test() throws InterruptedException {
        System.setProperty("org.slf4j.simpleLogger.log.method_execution_time.builtin.PersonService", "TRACE");
        Person person = new Person("John", "Mark");
        String fullName = personService.getFullName(person);
        assertThat(fullName, equalTo("John Mark"));
    }
}
