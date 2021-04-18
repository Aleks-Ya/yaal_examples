package method_execution_time.builtin;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Using standard Spring class PerformanceMonitorInterceptor for measuring method execution time.
 *
 * Source: https://www.baeldung.com/spring-performance-logging
 */
@RunWith(SpringRunner.class)
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
