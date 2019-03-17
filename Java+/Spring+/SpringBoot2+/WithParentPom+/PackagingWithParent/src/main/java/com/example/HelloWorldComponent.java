package com.example;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@SuppressWarnings("unused")
class HelloWorldComponent {

    @Value("${person}")
    private String person;

    @PostConstruct
    public void printHelloWorld() {
        System.out.println(StringUtils.upperCase("Hello World!"));
        System.out.println(StringUtils.lowerCase(person));
    }
}
