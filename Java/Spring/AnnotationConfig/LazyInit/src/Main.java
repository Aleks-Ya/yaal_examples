import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import scan.Address;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("scan");
        System.out.println("House was constructed eagerly.");
        System.out.println("Now will be constructed Address and Street:");
        context.getBean(Address.class);
    }
}
