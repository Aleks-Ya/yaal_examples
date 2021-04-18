package bean.partial;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Person.class, City.class})
public class PartialBeanTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void name() {
        Integer age = 25;
        String name = "John";
        Person person = ctx.getBean(Person.class, name, age);
        assertThat(person.toString(), equalTo("Person{name='John', age=25, city=SPb}"));
    }
}
