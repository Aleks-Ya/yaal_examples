package ru.yaal.examples.java.se.spring.contextinherit.inheritbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class InheritBean {
    public static void main(String args[]) {
        ApplicationContext parent = new GenericXmlApplicationContext(InheritBean.class, "parent.xml");
        GenericXmlApplicationContext child = new GenericXmlApplicationContext(InheritBean.class, "child.xml");
        child.setParent(parent);

        String fio = child.getBean("fio", String.class);
        System.out.printf("fio: %s\n", fio);
    }
}
