import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        List<MyClass> myList = (List<MyClass>) context.getBean("myList");
        for (MyClass my : myList) {
            System.out.println(my.getValue());
        }
    }
}
