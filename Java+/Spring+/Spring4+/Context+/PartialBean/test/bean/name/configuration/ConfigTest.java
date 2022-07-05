package bean.name.configuration;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
class ConfigTest {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Имя бина сгенерировано BeanNameGenerator.
     */
    @Test
    void generateName() {
        assertThat(ctx.getBean("generatedName")).isInstanceOf(BeanWithGeneratedName.class);
    }

    /**
     * Имя бина задано явно.
     */
    @Test
    void explicitName() {
        assertThat(ctx.getBean("explicitName")).isInstanceOf(BeanWithExplicitName.class);
    }

}