package ru.yaal.spring.mvc.controller.response;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.yaal.spring.mvc.config.WebAppContext;

@Configuration
@ComponentScan(basePackageClasses = {WebAppContext.class, ResponseController.class})
class ResponseControllerTestContext {
}
