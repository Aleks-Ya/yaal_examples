package bean.name.scanners;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {
        BeanWithGeneratedName.class,
        ComponentWithExplicitName.class,
        ServiceWithExplicitName.class,
        RepositoryWithExplicitName.class,
        ControllerWithExplicitName.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class Annotations {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Имя бина сгенерировано BeanNameGenerator.
     */
    @Test
    public void generateName() {
        assertEquals(BeanWithGeneratedName.class, ctx.getBean("beanWithGeneratedName").getClass());
    }

    /**
     * Имя бина задано явно.
     */
    @Test
    public void explicitName() {
        assertEquals(ComponentWithExplicitName.class, ctx.getBean("componentName").getClass());
        assertEquals(ServiceWithExplicitName.class, ctx.getBean("serviceName").getClass());
        assertEquals(RepositoryWithExplicitName.class, ctx.getBean("repositoryName").getClass());
        assertEquals(ControllerWithExplicitName.class, ctx.getBean("controllerName").getClass());
    }
}