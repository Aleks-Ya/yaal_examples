package bean.name.xml;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean/name/xml/spring-context.xml")
class XmlTest implements ApplicationContextAware {

    private ApplicationContext ctx;

    /**
     * Имя бина сгенерировано BeanNameGenerator.
     */
    @Test
    void generateName() {
        assertThat(ctx.getBean("bean.name.xml.BeanWithGeneratedNameXml#0")).isInstanceOf(BeanWithGeneratedNameXml.class);
    }

    /**
     * Имя бина задано явно.
     */
    @Test
    void explicitName() {
        assertThat(ctx.getBean("explicitName")).isInstanceOf(BeanWithExplicitName.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}