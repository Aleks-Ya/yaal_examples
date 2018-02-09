package ru.yaal.merch.bookshelf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ru.yaal.merch.bookshelf.service.impl")
class ServiceConfig {
}
