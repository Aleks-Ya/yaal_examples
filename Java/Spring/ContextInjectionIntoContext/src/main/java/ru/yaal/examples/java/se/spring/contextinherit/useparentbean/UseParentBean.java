package ru.yaal.examples.java.se.spring.contextinherit.useparentbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UseParentBean {
    public static void main(String args[]) {
        ApplicationContext parent = new GenericXmlApplicationContext("classpath:ru/yaal/examples/java/se/spring/contextinherit/useparentbean/parent.xml");

        GenericXmlApplicationContext child = new GenericXmlApplicationContext();
        child.load(UseParentBean.class, "child.xml");
        child.setParent(parent);
//        child.refresh();

        String fullFio = child.getBean("fullFio", String.class);
        System.out.printf("fullFio: %s\n", fullFio);
    }
}
