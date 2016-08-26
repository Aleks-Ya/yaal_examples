package ru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main class.
 */
@ComponentScan
@EnableAutoConfiguration
public class SecurityApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SecurityApplication.class, args);
    }
}