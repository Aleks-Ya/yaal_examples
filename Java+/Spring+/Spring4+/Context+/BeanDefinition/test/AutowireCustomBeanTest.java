import beans.Address;
import beans.House;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Autowire Spring dependencies in a bean after the context was started.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Address.class)
class AutowireCustomBeanTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void main() {
        assertThat(context).isNotNull();
        assertThat(context).isInstanceOf(ConfigurableApplicationContext.class);

        var configurableContext = (ConfigurableApplicationContext) context;

        var autowireFactory = configurableContext.getAutowireCapableBeanFactory();
        var house = (House) autowireFactory.autowire(House.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

        var address = context.getBean(Address.class);
        assertSame(address, house.getAddress());
    }
}