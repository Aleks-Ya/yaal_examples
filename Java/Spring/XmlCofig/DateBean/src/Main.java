import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        Date date = context.getBean("date", Date.class);
        System.out.println(date);

        Date dateEasy = context.getBean("dateEasy", Date.class);
        System.out.println(dateEasy);
    }
}