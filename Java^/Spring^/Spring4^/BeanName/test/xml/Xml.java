package xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration("classpath:xml/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Xml implements ApplicationContextAware {

    private ApplicationContext ctx;

    /**
     * Имя бина сгенерировано BeanNameGenerator.
     */
    @Test
    public void generateName() {
        assertEquals(BeanWithGeneratedNameXml.class,
                ctx.getBean("xml.BeanWithGeneratedNameXml#0").getClass());
    }

    /**
     * Имя бина задано явно.
     */
    @Test
    public void explicitName() {
        assertEquals(BeanWithExplicitName.class, ctx.getBean("explicitName").getClass());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}