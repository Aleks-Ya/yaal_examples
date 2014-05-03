import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        MyClass my1 = (MyClass) context.getBean("my1");
        MyClass my2 = (MyClass) context.getBean("my2");

        System.out.println(my1.getValue());
        System.out.println(my2.getValue());
    }
}
