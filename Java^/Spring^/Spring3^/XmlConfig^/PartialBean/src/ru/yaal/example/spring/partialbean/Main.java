package ru.yaal.example.spring.partialbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static java.lang.String.format;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
//        Parent p = context.getBean("parent", Parent.class);
//        System.out.println(format("Parent dateFormat: %s", p.getDateFormat()));
        Child c = (Child) context.getBean("child", new Date());
        System.out.println(format("Child dateFormat: %s", c.getDateFormat()));
        c.work();
    }
}
