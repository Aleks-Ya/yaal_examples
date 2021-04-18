package bean.name.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
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