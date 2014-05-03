import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        List<MyClass> myList = (List<MyClass>) context.getBean("myList");
        for (MyClass my : myList) {
            System.out.println(my.getValue());
        }

        Set<MyClass> mySet = (Set<MyClass>) context.getBean("mySet");
        for (MyClass my : mySet) {
            System.out.println(my.getValue());
        }

        Map<String, MyClass> myMap = (Map<String, MyClass>) context.getBean("myMap");
        for (MyClass my : myMap.values()) {
            System.out.println(my.getValue());
        }
    }
}
