package ru.yaal.spring.mvc.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableAutoConfiguration
@Import({PropertiesSettings.class, YamlSettings.class})
class ApplicationConfiguration {
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		Properties props = new Properties();
		props.put(IOException.class.getName(), "exception/io_error");

		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		resolver.setExceptionMappings(props);
		resolver.setDefaultErrorView("exception/unexpected_error");
		
		return resolver;
	}
}
