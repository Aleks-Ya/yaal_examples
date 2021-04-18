import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertSame;

public class BeanDefinition2Test {

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

    static class Address {
    }

    static class House {
        Address address;

        public Address getAddress() {
            return address;
        }
    }
}