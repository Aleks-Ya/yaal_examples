package bean.name.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigTest {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Имя бина сгенерировано BeanNameGenerator.
     */
    @Test
    public void generateName() {
        assertEquals(BeanWithGeneratedName.class,
                ctx.getBean("generatedName").getClass());
    }

    /**
     * Имя бина задано явно.
     */
    @Test
    public void explicitName() {
        assertEquals(BeanWithExplicitName.class,
                ctx.getBean("explicitName").getClass());
    }

}