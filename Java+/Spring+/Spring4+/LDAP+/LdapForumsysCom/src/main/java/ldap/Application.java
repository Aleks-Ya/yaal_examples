package ldap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ldap.repository.PersonRepo;

import java.util.List;

@ComponentScan(basePackageClasses = Application.class)
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        PersonRepo repo = context.getBean(PersonRepo.class);
        List<String> names = repo.getAllPersonNames();
        System.out.println("Names: \n" + names);
    }
}
