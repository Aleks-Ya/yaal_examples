import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.IMessageProvider;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().addActiveProfile("rough");
        context.scan("provider");
        System.out.println(context.getBean(IMessageProvider.class).getMessage());
    }
}