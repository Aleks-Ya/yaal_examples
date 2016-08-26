package ru.yaal.spring.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main class.
 */
@EnableAutoConfiguration
@ComponentScan
public class MvcApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MvcApplication.class, args);
	}
}