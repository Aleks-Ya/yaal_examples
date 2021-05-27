package bean.definition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Autowire Spring dependencies in a bean after the context was started.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Address.class)
public class AutowireCustomBeanTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void main() {
        assertNotNull(context);
        assertTrue(context instanceof ConfigurableApplicationContext);

        var configurableContext = (ConfigurableApplicationContext) context;

        var autowireFactory = configurableContext.getAutowireCapableBeanFactory();
        var house = (House) autowireFactory.autowire(House.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

        var address = context.getBean(Address.class);
        assertSame(address, house.getAddress());
    }
}