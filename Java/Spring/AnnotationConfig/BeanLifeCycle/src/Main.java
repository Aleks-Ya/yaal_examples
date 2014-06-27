import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Construct the context");
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("annotation-context.xml");

        System.out.println("\nAbstractApplicationContext.registerShutdownHook()");
        context.registerShutdownHook();
    }
}