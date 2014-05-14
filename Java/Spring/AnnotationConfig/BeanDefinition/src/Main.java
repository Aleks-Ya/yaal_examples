import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import scan.Address;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        MetadataReader reader = factory.getMetadataReader("scan.Address");
        BeanDefinition definition = new ScannedGenericBeanDefinition(reader);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("scan");
        context.registerBeanDefinition(Address.class.getName(), definition);

        System.out.println(context.getBean(Address.class.getName()));
    }
}