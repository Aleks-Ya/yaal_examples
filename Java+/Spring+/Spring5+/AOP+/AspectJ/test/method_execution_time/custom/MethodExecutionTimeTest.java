package method_execution_time.custom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Using custom Advisor for measuring method execution time.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProfilerConfig.class, PersonService.class})
@TestPropertySource(properties = "pointcut=execution(* method_execution_time.custom.PersonService.*(..))")
public class MethodExecutionTimeTest {

    @Autowired
    private PersonService personService;

    @Test
    public void test() throws InterruptedException {
        Person person = new Person("John", "Mark");
        String fullName = personService.getFullName(person);
        assertThat(fullName, equalTo("John Mark"));
    }
}
