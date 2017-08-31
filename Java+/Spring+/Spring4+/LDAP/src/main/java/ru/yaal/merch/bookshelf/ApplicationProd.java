package ru.yaal.merch.bookshelf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.yaal.merch.bookshelf.repository.PersonRepo;

import java.util.List;

@ComponentScan(basePackageClasses = ApplicationProd.class)
public class ApplicationProd {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationProd.class);
        PersonRepo repo = context.getBean(PersonRepo.class);
        List<String> names = repo.getAllPersonNames();
        System.out.println("Names: \n" + names);
    }
}
