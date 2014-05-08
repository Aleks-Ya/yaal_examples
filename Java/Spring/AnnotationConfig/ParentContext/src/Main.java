import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import scan1.Address;
import scan2.House;

public class Main {
    public static void main(String[] args) {
        {
            ApplicationContext parentContext = new ClassPathXmlApplicationContext("first_context.xml");
            Address address = parentContext.getBean(Address.class);
            System.out.println(address);

            String[] secondConfig = {"second_context.xml"};
            ApplicationContext childContext = new ClassPathXmlApplicationContext(secondConfig, parentContext);

            Address address2 = childContext.getBean(Address.class);
            System.out.println(address2);

            House house = childContext.getBean(House.class);
            System.out.println(house);
        }
    }
}