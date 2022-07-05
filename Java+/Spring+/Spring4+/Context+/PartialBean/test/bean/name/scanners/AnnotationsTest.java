package bean.name.scanners;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {
        BeanWithGeneratedName.class,
        ComponentWithExplicitName.class,
        ServiceWithExplicitName.class,
        RepositoryWithExplicitName.class,
        ControllerWithExplicitName.class
})
@RunWith(SpringJUnit4ClassRunner.class)
class AnnotationsTest {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Имя бина сгенерировано BeanNameGenerator.
     */
    @Test
    void generateName() {
        assertThat(ctx.getBean("beanWithGeneratedName")).isInstanceOf(BeanWithGeneratedName.class);
    }

    /**
     * Имя бина задано явно.
     */
    @Test
    void explicitName() {
        assertThat(ctx.getBean("componentName")).isInstanceOf(ComponentWithExplicitName.class);
        assertThat(ctx.getBean("serviceName")).isInstanceOf(ServiceWithExplicitName.class);
        assertThat(ctx.getBean("repositoryName")).isInstanceOf(RepositoryWithExplicitName.class);
        assertThat(ctx.getBean("controllerName")).isInstanceOf(ControllerWithExplicitName.class);
    }
}