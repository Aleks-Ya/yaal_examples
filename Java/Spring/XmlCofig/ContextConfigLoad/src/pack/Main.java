package pack;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import scan.Man;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        classpath();
        resource();
        annotation();
        staticContext();
        experiment();
    }

    private static void classpath() {
        ApplicationContext c1 = new ClassPathXmlApplicationContext("classpathConfig.xml");
        ApplicationContext c2 = new GenericXmlApplicationContext("classpath:classpathConfig.xml");
        ApplicationContext c3 = new GenericXmlApplicationContext("classpath:pack/inPackageConfig.xml");
        ApplicationContext c4 = new ClassPathXmlApplicationContext("pack/inPackageConfig.xml");
    }

    private static void resource() {
        {
            ApplicationContext context = new GenericXmlApplicationContext(Main.class, "resourceConfig.xml");
        }
        {
            Resource resource = new ClassPathResource("resourceConfig.xml", Main.class);
            ApplicationContext context = new GenericXmlApplicationContext(resource);
        }
        {
            URL url = Main.class.getResource("resourceConfig.xml");
            Resource resource = new FileSystemResource(url.getFile());
            ApplicationContext context = new GenericXmlApplicationContext(resource);
        }
        {
            URL url = Main.class.getResource("resourceConfig.xml");
            Resource resource = new UrlResource(url);
            ApplicationContext context = new GenericXmlApplicationContext(resource);
        }
        {
            /*
            не работает
            BeanDefinitionStoreException: Passed-in Resource [resource loaded through InputStream]
            contains an open stream: cannot determine validation mode automatically. Either pass in a Resource
            that is able to create fresh streams, or explicitly specify the validationMode
            on your XmlBeanDefinitionReader instance.
             */
//            InputStream is = Main.class.getResourceAsStream("resourceConfig.xml");
//            Resource resource = new InputStreamResource(is);
//            ApplicationContext context = new GenericXmlApplicationContext(resource);
        }
    }

    private static void annotation() {
        ApplicationContext context = new AnnotationConfigApplicationContext("scan");
        Man man = context.getBean(Man.class);
        System.out.println(man);
    }

    private static void staticContext() {
        StaticApplicationContext context = new StaticApplicationContext();
        context.registerSingleton("man", Man.class);
        Man man = context.getBean(Man.class);
        System.out.println(man);
    }

    private static void experiment() {
        //Filesystem
//        ApplicationContext fs1 = new FileSystemXmlApplicationContext("pack/inPackageConfig.xml");
    }
}
