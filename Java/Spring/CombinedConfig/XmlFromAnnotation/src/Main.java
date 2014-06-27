import annotation.City;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xml.Airport;

/**
 * Xml-конфиг подключается в annotation.Config с помощью @ImportResource.
 */
public class Main {
    public static void main(String[] args) {
        withAnnotationContextXml();
        withoutAnnotationContextXml();
    }

    private static void withoutAnnotationContextXml() {
        ApplicationContext context = new AnnotationConfigApplicationContext("annotation");

        Airport airport = context.getBean("airportXml", Airport.class);
        System.out.printf("АЭРОПОРТ из xml: %s%n%n", airport);

        City city = context.getBean("city", City.class);
        System.out.printf("ГОРОД из аннотаций и xml:%n%s", city);
    }

    private static void withAnnotationContextXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotation-context.xml");

        Airport airport = context.getBean("airportXml", Airport.class);
        System.out.printf("АЭРОПОРТ из xml: %s%n%n", airport);

        City city = context.getBean("city", City.class);
        System.out.printf("ГОРОД из аннотаций и xml:%n%s", city);
    }
}