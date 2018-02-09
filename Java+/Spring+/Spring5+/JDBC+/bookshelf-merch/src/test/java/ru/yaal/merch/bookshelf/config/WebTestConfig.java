package ru.yaal.merch.bookshelf.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import({RepoTestConfig.class, WebConfig.class})
public class WebTestConfig {
}
