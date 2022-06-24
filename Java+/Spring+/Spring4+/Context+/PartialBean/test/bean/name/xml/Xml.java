package bean.name.xml;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean/name/xml/spring-context.xml")
public class Xml implements ApplicationContextAware {

    private ApplicationContext ctx;

    /**
     * Имя бина сгенерировано BeanNameGenerator.
     */
    @Test
    void generateName() {
        assertEquals(BeanWithGeneratedNameXml.class,
                ctx.getBean("bean.name.xml.BeanWithGeneratedNameXml#0").getClass());
    }

    /**
     * Имя бина задано явно.
     */
    @Test
    void explicitName() {
        assertEquals(BeanWithExplicitName.class, ctx.getBean("explicitName").getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}