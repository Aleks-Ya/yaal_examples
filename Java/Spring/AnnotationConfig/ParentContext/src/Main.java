import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import scan1.Address;
import scan2.House;

public class Main {
    public static void main(String[] args) {
        ApplicationContext parentContext = new AnnotationConfigApplicationContext("scan1");
        Address address = parentContext.getBean(Address.class);
        System.out.println(address);

        AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
        childContext.setParent(parentContext);
        childContext.scan("scan2");
        childContext.refresh();

        Address address2 = childContext.getBean(Address.class);
        System.out.println(address2);

        House house = childContext.getBean(House.class);
        System.out.println(house);
    }
}