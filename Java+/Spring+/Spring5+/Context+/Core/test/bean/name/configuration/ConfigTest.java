package bean.name.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
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