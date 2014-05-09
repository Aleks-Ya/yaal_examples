import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.IMessageProvider;

/**
 * Способы выбрать активные профили:
 * 1. context.getEnvironment().addActiveProfile("rough");
 * 2. аргумент JVM: -Dspring.profiles.active=rough
 * 3. переменные окружения: spring.profiles.active=rough
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().addActiveProfile("rough");
        context.scan("provider");
        System.out.println(context.getBean(IMessageProvider.class).getMessage());
    }
}