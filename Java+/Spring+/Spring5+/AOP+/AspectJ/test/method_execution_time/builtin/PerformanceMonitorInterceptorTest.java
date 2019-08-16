package method_execution_time.builtin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AopConfiguration.class, PersonService.class})
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
