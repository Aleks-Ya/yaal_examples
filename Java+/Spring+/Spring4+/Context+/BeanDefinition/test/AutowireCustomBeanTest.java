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

import java.io.IOException;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Autowire Spring dependencies in a bean after the context was started.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Address.class)
public class AutowireCustomBeanTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void main() throws IOException {
        assertNotNull(context);
        assertTrue(context instanceof ConfigurableApplicationContext);

        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;

        AutowireCapableBeanFactory autowireFactory = configurableContext.getAutowireCapableBeanFactory();
        House house = (House) autowireFactory.autowire(House.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

        Address address = context.getBean(Address.class);
        assertSame(address, house.getAddress());
    }
}