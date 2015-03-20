import newbean.Address;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import scan.House;

import java.io.IOException;

import static org.junit.Assert.assertSame;

public class BeanDefinitionTest {

    /**
     * Класс Address сканируется и добавляется в контекст после
     * начальной инициализации контекста.
     */
    @Test
    public void main() throws IOException {
        MetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        MetadataReader reader = factory.getMetadataReader("newbean.Address");
        BeanDefinition definition = new ScannedGenericBeanDefinition(reader);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("scan");
        context.registerBeanDefinition(Address.class.getName(), definition);

        Address address = context.getBean(Address.class);
        House house = context.getBean(House.class);
        assertSame(address, house.getAddress());
    }
}