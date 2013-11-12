package ru.yaal.examples.gradle.configurations;

import org.joda.time.DateTime;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.format.datetime.joda.DateTimeFormatterFactoryBean;

public class ClassWithDependencies {
    public static void main(String[] args) {
        DateTime now = new DateTime();
        System.out.println(now);
        FactoryBean context = new DateTimeFormatterFactoryBean();
        System.out.println(context);
    }
}
