package bean.definition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Autowire Spring dependencies in a bean after the context was started.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Address.class)
class AutowireCustomBeanTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void test() {
        assertThat(context).isNotNull();
        assertThat(context).isInstanceOf(ConfigurableApplicationContext.class);

        var configurableContext = (ConfigurableApplicationContext) context;

        var autowireFactory = configurableContext.getAutowireCapableBeanFactory();
        var house = (House) autowireFactory.autowire(House.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

        var address = context.getBean(Address.class);
        assertThat(address).isSameAs(house.getAddress());
    }
}