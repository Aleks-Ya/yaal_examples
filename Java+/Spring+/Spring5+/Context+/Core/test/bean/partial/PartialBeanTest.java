package bean.partial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Person.class, City.class})
public class PartialBeanTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void name() {
        Integer age = 25;
        var name = "John";
        var person = ctx.getBean(Person.class, name, age);
        assertThat(person.toString(), equalTo("Person{name='John', age=25, city=SPb}"));
    }
}
