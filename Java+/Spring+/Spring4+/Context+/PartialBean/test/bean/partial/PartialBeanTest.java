package bean.partial;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
class PartialBeanTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    void name() {
        Integer age = 25;
        var name = "John";
        var person = ctx.getBean(Person.class, name, age);
        assertThat(person.toString()).isEqualTo("Person{name='John', age=25, city=SPb}");
    }
}
