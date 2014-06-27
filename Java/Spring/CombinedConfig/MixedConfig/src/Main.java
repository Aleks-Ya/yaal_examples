import annotation.City;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xml.Airport;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotation-context.xml");

        Airport airport = context.getBean("airportXml", Airport.class);
        System.out.printf("АЭРОПОРТ из xml: %s%n%n", airport);

        City city = context.getBean("city", City.class);
        System.out.printf("ГОРОД из аннотаций и xml:%n%s",city);
    }
}
